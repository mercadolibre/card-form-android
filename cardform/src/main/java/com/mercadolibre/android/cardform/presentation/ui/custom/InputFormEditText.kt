package com.mercadolibre.android.cardform.presentation.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import android.text.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.LinearLayout
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.model.InputData
import com.mercadolibre.android.cardform.presentation.model.TypeInput
import com.mercadolibre.android.ui.font.Font
import com.mercadolibre.android.ui.font.TypefaceHelper
import kotlinx.android.synthetic.main.cf_input_form_edittext.view.*

typealias OnTextChanged = (s: String) -> Unit

internal class InputFormEditText(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var infoHint: String = ""
    private var infoErrorHint: String = ""
    private var errorMessage: String? = null
    private var pattern: String = ""
    private var minLength = 0
    private var maxLength = 0
    private var hasError: Boolean = false
    private var maskWatcher: MaskWatcher? = null
    private var icon: Icon = Icon.EMPTY
    private var showIcons = true
    private var touchListener: () -> Unit = {}
    private var iconClickListener: () -> Unit = {}
    private var isTouched = false

    init {
        configureView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private fun configureView(context: Context) {
        orientation = VERTICAL
        inflate(context, R.layout.cf_input_form_edittext, this)

        input.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (isTouched) {
                isTouched = false
                touchListener()
            }
            if (hasFocus) {
                input.setSelection(getText().length)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (!hasFocus()) {
            isTouched = true
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setInitializeAccessibilityFunction(
        onInitializeAccessibility: (
            host: View?,
            info: AccessibilityNodeInfo?
        ) -> Unit
    ) {
        input.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View?,
                info: AccessibilityNodeInfo?
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                onInitializeAccessibility(host, info)
            }
        })
    }

    fun setInputType(inputType: Int) {
        input.inputType = inputType
    }

    fun setHint(hint: String) {
        //for accessibility
        contentDescription = hint
        inputLayout.hint = hint
    }

    fun setInfoHint(info: String) {
        infoHint = info
    }

    fun setFilters(filters: Array<InputFilter>) {
        input.filters = filters
    }

    fun hasError() = hasError

    private fun getColorStateUnderLine(color: Int) = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_focused)
        ),
        intArrayOf(
            ContextCompat.getColor(context, R.color.cf_hint_input_text),
            ContextCompat.getColor(context, color)
        )
    )

    fun showError(message: String? = null) {
        infoInput.post {
            infoInput.text = if (message.isNullOrEmpty()) infoErrorHint else message
            infoInput.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.ui_components_error_color
                )
            )
            TypefaceHelper.setTypeface(infoInput, Font.SEMI_BOLD)
            ViewCompat.setBackgroundTintList(
                input,
                getColorStateUnderLine(R.color.ui_components_error_color)
            )
            errorMessage = infoInput.text.toString()
            announceForAccessibility(errorMessage)
        }
        hasError = true
    }

    fun showIconActions(show: Boolean) {
        showIcons = show
    }

    fun clearError() {
        if (hasError) {
            infoInput.text = infoHint
            infoInput.setTextColor(ContextCompat.getColor(context, R.color.cf_hint_input_text))
            TypefaceHelper.setTypeface(infoInput, Font.REGULAR)
            ViewCompat.setBackgroundTintList(
                input,
                getColorStateUnderLine(R.color.ui_components_primary_color)
            )
            errorMessage = null
            hasError = false
        }
    }

    fun setMessageError(message: String) {
        infoErrorHint = message
    }

    fun getText(): String {
        return input.text.toString().trim()
    }

    fun setText(text: String) {
        with(input) {
            setText(text)
            this.text?.let { setSelection(it.length) }
        }
    }

    fun setMinLength(minLength: Int) {
        this.minLength = minLength
    }

    fun setMaxLength(maxLength: Int) {
        this.maxLength = maxLength
    }

    fun isNotEmpty(): Boolean {
        return input.text?.isNotEmpty() ?: false
    }

    fun isComplete(): Boolean {
        return input.text?.trim()?.length == maxLength
    }

    fun getMaxLength() = maxLength

    fun addMaskWatcher(mask: String, textChanged: OnTextChanged) {
        maskWatcher?.apply {
            input.removeTextChangedListener(this)

            addWatcher(mask, textChanged)

            input.text?.apply {
                if (isNotEmpty() && mask.isNotEmpty()) {
                    var newText = mask
                    val holdText = replace("[^A-Za-z0-9]+".toRegex(), "")
                    for (i in holdText.indices) {
                        newText = newText.replaceFirst('$', holdText[i])
                    }

                    update(this, newText.substringBefore('$')) {
                        setText(it)
                    }
                }
            }
        } ?: addWatcher(mask, textChanged)
    }

    private fun addWatcher(mask: String, textChanged: OnTextChanged) {
        maskWatcher = object : MaskWatcher(mask) {

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                charSequence?.let {
                    configureInternalInput(getText(), textChanged)
                } ?: addRightCancelDrawable(0)
            }
        }
        input.addTextChangedListener(maskWatcher)
    }

    fun configure(data: InputData, textChanged: OnTextChanged) {
        setInputType(TypeInput.fromType(data.type).getInputType())
        setHint(data.title)
        data.hintMessage?.let { setInfoHint(it) }
        setMessageError(data.validationMessage)
        setPattern(data.validationPattern ?: "")

        if (hasError) {
            showError(errorMessage)
        } else {
            infoInput.text = infoHint
        }

        var mask = ""
        var minLength = 0
        val maxLength: Int

        if (!data.mask.isNullOrEmpty()) {
            mask = data.mask!!
            maxLength = mask.length
            minLength = maxLength - 1
        } else {
            maxLength = data.maxLength
        }

        setFilters(arrayOf(InputFilter.LengthFilter(maxLength)))
        setMinLength(minLength)
        setMaxLength(maxLength)

        addMaskWatcher(mask, textChanged)
    }

    private fun setPattern(pattern: String) {
        this.pattern = pattern
    }

    fun validate(): Boolean {
        val text = getText()
        return text.isNotEmpty() && text.length in minLength..maxLength && validatePattern()
    }

    fun validatePattern(): Boolean {
        return pattern.isEmpty() || Regex(pattern).matches(getText())
    }

    fun addFilters(newFilters: Array<InputFilter>) {
        with(input) {
            val newSetFilters = filters.toMutableSet()
            newSetFilters.addAll(newFilters)
            filters = newSetFilters.toTypedArray()
        }
    }

    fun saveState(savedState: Boolean) {
        input.isSaveEnabled = savedState
    }

    private fun configureInternalInput(text: String, textChanged: OnTextChanged) {
        if (showIcons && text.isNotEmpty()) {
            addRightCancelDrawable(R.drawable.cf_icon_close)
        } else {
            addRightCancelDrawable(0)
        }

        textChanged.invoke(text)
    }

    fun addRightCheckDrawable(@DrawableRes iconCancel: Int) {
        val check: Drawable? = ContextCompat.getDrawable(context, iconCancel)
        check?.setBounds(0, 0, check.intrinsicWidth, check.intrinsicHeight)
        input.setCompoundDrawables(null, null, check, null)
        input.addRightDrawableClicked(null)
        icon = Icon.CHECK
    }

    @SuppressLint("ResourceType")
    fun addRightCancelDrawable(@DrawableRes iconCancel: Int) {
        var cancel: Drawable? = null
        icon = if (iconCancel > 0) {
            cancel = ContextCompat.getDrawable(context, iconCancel)
            cancel?.setBounds(0, 0, cancel.intrinsicWidth, cancel.intrinsicHeight)
            input.addRightDrawableClicked { clearError(); it.setText(""); iconClickListener() }
            Icon.CLEAR
        } else {
            Icon.EMPTY
        }
        input.setCompoundDrawables(null, null, cancel, null)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = (state as InputSavedState)
        super.onRestoreInstanceState(savedState.superState)
        isFocusableInTouchMode = savedState.getIsFocusableInTouchMode()
        hasError = savedState.hasError()
        errorMessage = savedState.getErrorMessage()
        showIcons = savedState.showIcon()
        if (showIcons) {
            when (savedState.getIcon()) {
                Icon.EMPTY -> {
                    addRightCancelDrawable(0)
                }

                Icon.CLEAR -> {
                    addRightCancelDrawable(R.drawable.cf_icon_close)
                }

                Icon.CHECK -> {
                    addRightCheckDrawable(R.drawable.cf_icon_check)
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return InputSavedState(
            super.onSaveInstanceState(),
            isFocusableInTouchMode,
            hasError,
            errorMessage,
            icon,
            showIcons
        )
    }

    fun addOnTouchListener(touchListener: () -> Unit) {
        this.touchListener = touchListener
    }

    fun addOnIconClickListener(iconClickListener: () -> Unit) {
        this.iconClickListener = iconClickListener
    }

    internal class InputSavedState : BaseSavedState {
        private var isFocusable: Boolean
        private var hasError: Boolean
        private var errorMessage: String?
        private var icon: Icon
        private var showIcon: Boolean

        constructor(source: Parcel?) : super(source) {
            isFocusable = source?.readByte() != 0.toByte()
            hasError = source?.readByte() != 0.toByte()
            errorMessage = source?.readString()
            icon = Icon.fromType(source?.readInt() ?: 0)
            showIcon = source?.readByte() != 0.toByte()
        }

        constructor(
            superState: Parcelable?,
            isFocusable: Boolean,
            hasError: Boolean,
            errorMessage: String?,
            drawable: Icon,
            showIcon: Boolean
        ) : super(
            superState
        ) {
            this.isFocusable = isFocusable
            this.hasError = hasError
            this.errorMessage = errorMessage
            this.icon = drawable
            this.showIcon = showIcon
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeByte(if (isFocusable) 1 else 0)
            parcel.writeByte(if (hasError) 1 else 0)
            parcel.writeString(errorMessage)
            parcel.writeInt(icon.ordinal)
            parcel.writeByte(if (showIcon) 1 else 0)
        }

        fun getIsFocusableInTouchMode() = isFocusable
        fun hasError() = hasError
        fun getErrorMessage() = errorMessage
        fun getIcon() = icon
        fun showIcon() = showIcon

        override fun describeContents() = 0

        companion object CREATOR : Parcelable.Creator<InputSavedState> {
            override fun createFromParcel(parcel: Parcel) = InputSavedState(parcel)
            override fun newArray(size: Int) = arrayOfNulls<InputSavedState?>(size)
        }
    }

    internal enum class Icon {
        EMPTY,
        CLEAR,
        CHECK;

        companion object {
            fun fromType(type: Int): Icon {
                values().forEach {
                    if (it.ordinal == type) {
                        return it
                    }
                }

                throw IllegalArgumentException("$type is not valid argument")
            }
        }
    }

    companion object {
        const val LENGTH_DEFAULT = 40
    }
}
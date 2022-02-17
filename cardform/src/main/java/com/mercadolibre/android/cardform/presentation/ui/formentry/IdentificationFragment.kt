package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.databinding.FragmentIdentificationBinding
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.preferences.IdentificationPreferences
import com.mercadolibre.android.cardform.presentation.delegate.viewBinding
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.model.Identification
import com.mercadolibre.android.cardform.presentation.model.TypeInput
import com.mercadolibre.android.cardform.presentation.ui.IdentificationUtils
import com.mercadolibre.android.cardform.presentation.ui.custom.InputFormEditText
import com.mercadolibre.android.cardform.presentation.ui.custom.InputFormEditText.Companion.LENGTH_DEFAULT
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.flow.BackTrack
import com.mercadolibre.android.cardform.tracks.model.flow.NextTrack
import com.mercadolibre.android.cardform.tracks.model.identification.IdentificationInvalidTrack
import com.mercadolibre.android.cardform.tracks.model.identification.IdentificationValidTrack
import com.mercadolibre.android.cardform.tracks.model.identification.IdentificationView


/**
 * A simple [Fragment] subclass.
 */
internal class IdentificationFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_identification

    private val identificationFragmentBinding by viewBinding(FragmentIdentificationBinding::bind)

    private lateinit var preferences: IdentificationPreferences
    private var lastPositionSelected = 0
    private var isFirstTime = false
    private var isIdentificationTracked = false
    private var populate = false
    private var identificationEditText: InputFormEditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(rootLayout, container, false)
    }

    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        override fun onItemSelected(
            parent: AdapterView<*>?, view: View?, position: Int,
            id: Long
        ) {
            (identificationFragmentBinding.identificationTypes.adapter.getItem(position) as Identification?)?.let {
                if (position != lastPositionSelected) {
                    lastPositionSelected = position
                    identificationEditText?.setText("")
                }
                configureInput(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Dependencies.instance.localPreferences!!.identificationPreferences
        identificationEditText = view.findViewById(R.id.identificationEditText)
        if (savedInstanceState == null) {
            isFirstTime = true
        } else {
            lastPositionSelected = savedInstanceState.getInt(LAST_POSITION_EXTRA)
            isIdentificationTracked =
                savedInstanceState.getBoolean(TRACK_IDENTIFICATION_VIEW, false)
            populate = savedInstanceState.getBoolean(POPULATE, false)
            isFirstTime = false
        }

        identificationEditText?.apply {
            showIconActions(false)
            setInitializeAccessibilityFunction { host, info ->

                val currentText = getText()
                val textAccessibility = (host as TextInputEditText).hint
                if (currentText.isNotEmpty()) {
                    info?.text = info?.text?.let {
                        var newText = it
                        var textSplit: List<String>

                        if (it.contains("/")) {
                            textSplit = it.split("/")
                            newText = "${textSplit[0]} / ${textSplit[1]}"
                        }

                        if (newText.contains("-")) {
                            textSplit = newText.split("-")
                            newText = "${textSplit[0]} ${textSplit[1]}"
                        }

                        newText
                    }
                } else {
                    info?.text = textAccessibility
                }
            }
        }
    }

    override fun bindViewModel() {
        viewModel.identificationTypesLiveData.nonNullObserve(viewLifecycleOwner) { data ->
            identificationEditText?.apply {
                setHint(data!!.title)
                setMessageError(data.validationMessage)
            }

            val listIdentification = data!!.identifications
            val identificationAdapter = ArrayAdapter(
                context!!,
                R.layout.cf_spinner_identification,
                R.id.identificationText,
                listIdentification
            )
            identificationAdapter.setDropDownViewResource(R.layout.cf_custom_drop_down_spinner)
            identificationFragmentBinding.identificationTypes.adapter = identificationAdapter
            identificationFragmentBinding.identificationTypes.onItemSelectedListener = onItemSelectedListener

            if (isFirstTime) {
                with(preferences) {
                    if (getIdentificationId().isNotEmpty() && getIdentificationNumber().isNotEmpty() && data.autocomplete) {
                        selectSpinnerItemByValue(getIdentificationId(), getIdentificationNumber())
                    }
                }
            } else {
                identificationFragmentBinding.identificationTypes.setSelection(lastPositionSelected)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_POSITION_EXTRA, identificationFragmentBinding.identificationTypes.selectedItemPosition)
        outState.putBoolean(TRACK_IDENTIFICATION_VIEW, populate)
        outState.putBoolean(POPULATE, populate)
    }

    private fun selectSpinnerItemByValue(id: String, number: String) {
        val adapter = identificationFragmentBinding.identificationTypes.adapter
        for (position in 0 until adapter.count) {
            val identification = (adapter.getItem(position) as Identification)
            if (identification.id == id) {
                identificationEditText?.apply {
                    lastPositionSelected = position
                    identificationFragmentBinding.identificationTypes.setSelection(lastPositionSelected)
                    setText(number)
                    setMaxLength(number.length)
                    isInputValid =
                        IdentificationUtils.validate(
                            number.filter { it.isLetterOrDigit() },
                            identification
                        )
                    populate = true
                }
                return
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            identificationEditText?.apply {
                post { sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED) }
            }
        }
    }

    override fun focusableInTouchMode(focusable: Boolean) {
        identificationEditText?.apply {
            isFocusableInTouchMode = focusable
            if (!focusable) {
                requestFocus()
            }
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        super.toNext(position, move)
        identificationEditText?.getText()?.let { number ->
            with(viewModel) {
                if (isInputValid) {
                    cardStepInfo.identificationNumber = number
                    preferences.saveIdentificationId(cardStepInfo.identificationId)
                    tracker.trackEvent(IdentificationValidTrack())
                    tracker.trackEvent(NextTrack(TrackSteps.IDENTIFICATION.getType()))
                    preferences.saveIdentificationNumber(number)
                } else {
                    tracker.trackEvent(
                        IdentificationInvalidTrack(
                            cardStepInfo.identificationId,
                            number
                        )
                    )
                }
            }
        }
    }

    override fun toBack(position: Int, move: MoveTo) {
        super.toBack(position, move)
        viewModel.tracker.trackEvent(BackTrack(TrackSteps.IDENTIFICATION.getType()))
    }

    override fun showError() {
        identificationEditText?.showError()
    }

    private fun configureInput(data: Identification) {
        viewModel.cardStepInfo.identificationId = data.id
        identificationEditText?.apply {
            var mask = ""
            val maxLength: Int
            val minLength: Int

            if (!data.mask.isNullOrEmpty()) {
                mask = data.mask
                maxLength = mask.length
                minLength = maxLength - 1
            } else {
                maxLength = if (data.maxLength > 0) data.maxLength else LENGTH_DEFAULT
                minLength = data.minLength
            }

            setInputType(TypeInput.fromType(data.type).getInputType())
            setFilters(arrayOf(InputFilter.LengthFilter(maxLength)))
            setMinLength(minLength)
            setMaxLength(maxLength)

            if (hasError()) {
                showError()
            }

            addMaskWatcher(mask) {
                isInputValid =
                    IdentificationUtils.validate(it.filter { c -> c.isLetterOrDigit() }, data)

                clearError()
            }
        }
    }

    override fun refreshData() {
        identificationEditText?.setText("")
    }

    override fun trackFragmentView() {
        viewModel.tracker.trackView(IdentificationView(populate))
    }

    override fun getSharedViewModelScope() = parentFragment!!

    companion object {
        private const val LAST_POSITION_EXTRA = "last_position"
        private const val TRACK_IDENTIFICATION_VIEW = "track_identification_view"
        private const val POPULATE = "populate"
    }
}
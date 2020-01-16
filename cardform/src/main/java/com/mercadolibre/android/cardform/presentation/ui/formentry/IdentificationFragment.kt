package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.preferences.IdentificationPreferences
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.model.Identification
import com.mercadolibre.android.cardform.presentation.model.TypeInput
import com.mercadolibre.android.cardform.presentation.ui.IdentificationUtils
import com.mercadolibre.android.cardform.presentation.ui.custom.InputFormEditText.Companion.LENGTH_DEFAULT
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.flow.BackTrack
import com.mercadolibre.android.cardform.tracks.model.flow.NextTrack
import com.mercadolibre.android.cardform.tracks.model.identification.IdentificationInvalidTrack
import com.mercadolibre.android.cardform.tracks.model.identification.IdentificationValidTrack
import com.mercadolibre.android.cardform.tracks.model.identification.IdentificationView
import kotlinx.android.synthetic.main.cf_input_form_edittext.view.*
import kotlinx.android.synthetic.main.fragment_identification.*

/**
 * A simple [Fragment] subclass.
 */
class IdentificationFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_identification
    private lateinit var preferences: IdentificationPreferences
    private var lastPositionSelected = 0
    private var isFirstTime = false
    private var isIdentificationTracked = false
    private var populate = false
    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        override fun onItemSelected(
            parent: AdapterView<*>?, view: View?, position: Int,
            id: Long
        ) {
            (identificationTypes.adapter.getItem(position) as Identification?)?.let {
                if (position != lastPositionSelected) {
                    identificationEditText.setText("")
                }
                configureInput(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Dependencies.instance.localPreferences!!.identificationPreferences
        if (savedInstanceState == null) {
            isFirstTime = true
        } else {
            lastPositionSelected = savedInstanceState.getInt(LAST_POSITION_EXTRA)
            isIdentificationTracked =
                savedInstanceState.getBoolean(TRACK_IDENTIFICATION_VIEW, false)
            populate = savedInstanceState.getBoolean(POPULATE, false)
            isFirstTime = false
        }
        identificationEditText.showIconActions(false)
    }

    override fun bindViewModel() {
        viewModel.identificationTypesLiveData.nonNullObserve(viewLifecycleOwner) { data ->
            identificationEditText.apply {
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
            identificationTypes.adapter = identificationAdapter
            identificationTypes.onItemSelectedListener = onItemSelectedListener

            if (isFirstTime) {
                with(preferences) {
                    if (getIdentificationId().isNotEmpty() && getIdentificationNumber().isNotEmpty()) {
                        selectSpinnerItemByValue(getIdentificationId(), getIdentificationNumber())
                    }
                }
            } else {
                identificationTypes.setSelection(lastPositionSelected)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_POSITION_EXTRA, identificationTypes.selectedItemPosition)
        outState.putBoolean(TRACK_IDENTIFICATION_VIEW, populate)
        outState.putBoolean(POPULATE, populate)
    }

    private fun selectSpinnerItemByValue(id: String, number: String) {
        val adapter = identificationTypes.adapter
        for (position in 0 until adapter.count) {
            val identification = (adapter.getItem(position) as Identification)
            if (identification.id == id) {
                with(identificationEditText) {
                    lastPositionSelected = position
                    identificationTypes.setSelection(lastPositionSelected)
                    setText(number)
                    setMaxLength(number.length)
                    isInputValid =
                        IdentificationUtils.validate(number.filter { it.isDigit() }, identification)
                    populate = true
                }
                return
            }
        }
    }

    override fun focusableInTouchMode(focusable: Boolean) {
        identificationEditText.isFocusableInTouchMode = focusable
        if (!focusable) {
            identificationEditText.requestFocus()
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        super.toNext(position, move)
        val number = identificationEditText.getText()

        with(viewModel) {
            if (isInputValid) {
                cardStepInfo.identificationNumber = number
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

    override fun toBack(position: Int, move: MoveTo) {
        super.toBack(position, move)
        viewModel.tracker.trackEvent(BackTrack(TrackSteps.IDENTIFICATION.getType()))
    }

    override fun showError() = identificationEditText.showError()

    private fun configureInput(data: Identification) {
        viewModel.cardStepInfo.identificationId = data.id
        preferences.saveIdentificationId(data.id)
        with(identificationEditText) {
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
            input.inputType = TypeInput.fromType(data.type).getInputType()
            setFilters(arrayOf(InputFilter.LengthFilter(maxLength)))
            setMinLength(minLength)
            setMaxLength(maxLength)

            if (hasError()) {
                showError()
            }

            addMaskWatcher(mask) {
                isInputValid = IdentificationUtils.validate(it.filter { c -> c.isDigit() }, data)

                if (hasError()) {
                    clearError()
                }
            }
        }
    }

    override fun refreshData() {
        identificationEditText.setText("")
    }

    override fun trackFragmentView() {
        viewModel.tracker.trackView(IdentificationView(populate))
    }

    companion object {
        private const val LAST_POSITION_EXTRA = "last_position"
        private const val TRACK_IDENTIFICATION_VIEW = "track_identification_view"
        private const val POPULATE = "populate"
    }
}
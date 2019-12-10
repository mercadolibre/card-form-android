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
import com.mercadolibre.android.cardform.presentation.ui.custom.InputFormEditText.Companion.LENGTH_DEFAULT
import kotlinx.android.synthetic.main.fragment_identification.*

/**
 * A simple [Fragment] subclass.
 */
class IdentificationFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_identification
    private lateinit var preferences: IdentificationPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Dependencies.instance.localPreferences!!.identificationPreferences
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
            identificationTypes.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int,
                        id: Long
                    ) {
                        identificationAdapter.getItem(position)?.let { configureInput(it) }
                    }
                }

            if (identificationEditText.getText().isEmpty()) {
                with(preferences) {
                    if (getIdentificationId().isNotEmpty() && getIdentificationNumber().isNotEmpty()) {
                        selectSpinnerItemByValue(getIdentificationId(), getIdentificationNumber())
                    }
                }
            }
        }
    }

    private fun selectSpinnerItemByValue(id: String, number: String) {
        val adapter = identificationTypes.adapter
        for (position in 0 until adapter.count) {
            if ((adapter.getItem(position) as Identification).id == id) {
                with(identificationEditText) {
                    identificationTypes.setSelection(position)
                    setText(number)
                    setMaxLength(number.length)
                    isInputValid = validate()
                }
                return
            }
        }
    }

    override fun focusableInTouchMode(focusable: Boolean) {
        identificationEditText.setIsFocusableInTouchMode(focusable)
        if (!focusable) {
            identificationEditText.requestFocus()
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        super.toNext(position, move)
        if (isInputValid) {
            val number = identificationEditText.getText()
            viewModel.cardStepInfo.identificationNumber = number
            preferences.saveIdentificationNumber(number)
        }
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
            setFilters(arrayOf(InputFilter.LengthFilter(maxLength)))
            setMinLength(minLength)
            setMaxLength(maxLength)

            if (hasError()) {
                showError()
            }

            addMaskWatcher(mask) {
                isInputValid = identificationEditText.validate()

                if (identificationEditText.hasError()) {
                    identificationEditText.clearError()
                }
            }
        }
    }
}
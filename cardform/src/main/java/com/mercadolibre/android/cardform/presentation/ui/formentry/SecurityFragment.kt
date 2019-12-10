package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.view.View
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.CardFilledData
import com.mercadolibre.android.cardform.presentation.model.CardState
import kotlinx.android.synthetic.main.fragment_security.*
import java.util.*


class SecurityFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_security

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expirationEditText.saveState(false)
        cvvCodeEditText.saveState(false)
        savedInstanceState?.apply {
            expirationEditText.setText(getString(EXTRA_EXPIRATION_TEXT, ""))
            cvvCodeEditText.setText(getString(EXTRA_CODE_TEXT, ""))
        }

        if (savedInstanceState == null) {
            viewModel.expirationLiveData.value =
                ObjectStepFactory.createDefaultStepFrom(resources, FormType.EXPIRATION_TYPE)
            viewModel.codeLiveData.value =
                ObjectStepFactory.createDefaultStepFrom(resources, FormType.SECURITY_CODE_TYPE)
        } else {
            savedInstanceState.apply {
                expirationEditText.setText(getString(EXTRA_EXPIRATION_TEXT, ""))
                cvvCodeEditText.setText(getString(EXTRA_CODE_TEXT, ""))
            }
        }
    }

    override fun bindViewModel() {

        viewModel.expirationLiveData.nonNullObserve(viewLifecycleOwner) { data ->
            expirationEditText.configure(data) {
                viewModel.updateInputData(CardFilledData.ExpirationDate(it))

                if (it.isNotEmpty() && it.last().isDigit()) {
                    if (!isValidDate()) {
                        expirationEditText.showError()
                    } else {
                        expirationEditText.clearError()
                    }
                }
            }
        }

        viewModel.codeLiveData.nonNullObserve(viewLifecycleOwner) { data ->
            cvvCodeEditText.configure(data) {
                viewModel.updateInputData(CardFilledData.Cvv(it))

                if (cvvCodeEditText.hasError()) {
                    cvvCodeEditText.clearError()
                }
            }
        }
    }

    override fun fromRight() {
        super.fromRight()
        viewModel.stateCardLiveData.value = CardState.ShowCode
        focusCvvCodeInput()
    }


    override fun fromLeft() {
        super.fromLeft()
        focusExpirationInput()
    }

    private fun validateExpirationDate(): Boolean {
        viewModel.cardStepInfo.expiration = expirationEditText.getText()
        return expirationEditText.isNotEmpty() && expirationEditText.validatePattern() && isValidDate()
    }

    private fun validateCvvCode(): Boolean {
        viewModel.cardStepInfo.code = cvvCodeEditText.getText()
        return cvvCodeEditText.isNotEmpty() && cvvCodeEditText.isComplete() && cvvCodeEditText.validatePattern()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_EXPIRATION_TEXT, expirationEditText.getText())
        outState.putString(EXTRA_CODE_TEXT, cvvCodeEditText.getText())
    }

    private fun isValidDate(): Boolean {

        val expiration = expirationEditText.getText().split('/')

        if (expiration.size == 1) {
            return expiration[0].toInt() in 1..12
        }

        if (expiration[0].toInt() in 1..12 && expiration[1].matches(Regex("[0-9]{2}"))) {
            val monthExpiration = expiration[0].toInt()
            val yearExpiration = "20${expiration[1]}".toInt()
            val date = Date()
            val cal = Calendar.getInstance(TimeZone.getTimeZone(TimeZone.getDefault().id))
            cal.time = date
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1

            if (yearExpiration >= year) {
                return if (yearExpiration == year) {
                    monthExpiration >= month
                } else {
                    true
                }
            }
            return false
        }
        return false
    }

    override fun toNext(position: Int, move: ((nextPosition: Int) -> Unit)) {
        if (expirationHasFocus()) {

            if (validateExpirationDate()) {
                viewModel.stateCardLiveData.value = CardState.ShowCode
                super.fromLeft()
                focusCvvCodeInput()
            } else {
                expirationEditText.showError()
            }

        } else if (validateCvvCode()) {
            viewModel.stateCardLiveData.value = CardState.HideCode
            move(position)
        } else {
            cvvCodeEditText.showError()
        }
    }

    override fun toBack(position: Int, move: ((previousPosition: Int) -> Unit)) {
        if (cvvCodeHasFocus()) {
            super.fromRight()
            focusExpirationInput()
            viewModel.stateCardLiveData.value = CardState.HideCode
        } else {
            move(position)
        }
    }

    private fun cvvCodeHasFocus(): Boolean = cvvCodeEditText.hasFocus()
    private fun expirationHasFocus(): Boolean = expirationEditText.hasFocus()
    private fun focusExpirationInput() = expirationEditText.requestFocus()
    private fun focusCvvCodeInput() = cvvCodeEditText.requestFocus()

    companion object {
        private const val EXTRA_EXPIRATION_TEXT = "expiration_text"
        private const val EXTRA_CODE_TEXT = "code_text"
    }
}
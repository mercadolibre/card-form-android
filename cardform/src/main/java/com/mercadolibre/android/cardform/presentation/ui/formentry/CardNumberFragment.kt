package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.view.View

import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.CardFilledData
import com.mercadolibre.android.cardform.presentation.ui.custom.Luhn
import kotlinx.android.synthetic.main.fragment_number_card.*

/**
 * A simple [Fragment] subclass.
 */
class CardNumberFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_number_card

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isVisible) {
            numberCardEditText.requestFocus()
        }
        if (savedInstanceState == null) {
            viewModel.numberLiveData.value =
                ObjectStepFactory.createDefaultStepFrom(resources, FormType.CARD_NUMBER.getType())
        }
        numberCardEditText.showIconActions(false)

        val filter = InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                val char = source[i]
                if (!Character.isDigit(char) && !Character.isSpaceChar(char)) {
                    return@InputFilter ""
                }
            }
            null
        }

        numberCardEditText.addFilters(arrayOf(filter))
    }

    override fun bindViewModel() {
        with(viewModel) {
            numberLiveData.nonNullObserve(viewLifecycleOwner) { data ->
                numberCardEditText.configure(data) {

                    if (numberCardEditText.hasError()) {
                        numberCardEditText.clearError()
                    }

                    updateInputData(CardFilledData.Number(it))
                    onCardNumberChange(it.replace("\\s+".toRegex(), ""))

                    isInputValid = if (numberCardEditText.validatePattern()) {
                        if (Luhn.isValid(it)) {
                            numberCardEditText.addRightCheckDrawable(R.drawable.cf_icon_check)
                            true
                        } else {
                            numberCardEditText.showError()
                            false
                        }
                    } else {
                        false
                    }
                }
            }
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        if (isInputValid) {
            with(viewModel) {
                if (cardLiveData.value != null) {
                    move.invoke(position)
                    cardStepInfo.cardNumber = numberCardEditText.getText()
                } else {
                    retryFetchCard(context)
                }
            }
        } else {
            showError()
        }
    }

    override fun showError() {
        val text = numberCardEditText.getText()
        numberCardEditText.showError(if (text.isEmpty()) getString(R.string.cf_complete_field) else text)
    }
}
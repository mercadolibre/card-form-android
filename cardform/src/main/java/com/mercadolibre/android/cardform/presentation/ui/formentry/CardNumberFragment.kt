package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.model.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.CardData
import com.mercadolibre.android.cardform.presentation.ui.custom.Luhn
import kotlinx.android.synthetic.main.fragment_number_card.*

/**
 * A simple [Fragment] subclass.
 */
class CardNumberFragment: InputFragment() {

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
    }

    override fun bindViewModel() {
        with(viewModel) {
            numberLiveData.nonNullObserve(viewLifecycleOwner) { data ->
                numberCardEditText.configure(data) {

                    if (numberCardEditText.hasError()) {
                        numberCardEditText.clearError()
                    }

                    updateInputData(CardData.CardNumber(it))
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
        super.toNext(position, move)
        if (isInputValid) {
            viewModel.cardStepInfo.cardNumber = numberCardEditText.getText()
        }
    }

    override fun showError() = numberCardEditText.showError()
}
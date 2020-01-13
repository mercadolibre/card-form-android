package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.CardFilledData
import com.mercadolibre.android.cardform.presentation.ui.custom.Luhn
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.bin.BinClearTrack
import com.mercadolibre.android.cardform.tracks.model.bin.BinInvalidTrack
import com.mercadolibre.android.cardform.tracks.model.bin.BinNumberView
import com.mercadolibre.android.cardform.tracks.model.bin.BinValidTrack
import com.mercadolibre.android.cardform.tracks.model.flow.BackTrack
import com.mercadolibre.android.cardform.tracks.model.flow.NextTrack
import kotlinx.android.synthetic.main.fragment_number_card.*

/**
 * A simple [Fragment] subclass.
 */
class CardNumberFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_number_card

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            trackFragmentView()
            setDefaultConfiguration()
        }

        numberCardEditText.addOnIconClickListener {
            viewModel.tracker.trackEvent(BinClearTrack())
        }

        if (isVisible) {
            numberCardEditText.requestFocus()
        }
    }


    private fun setDefaultConfiguration() {
        numberCardEditText.configure(
            ObjectStepFactory.createDefaultStepFrom(
                resources,
                FormType.CARD_NUMBER.getType()
            )
        ) {
            resolveState(it)
            isInputValid = sanitizeBin(it).length >= MIN_LENGTH_BIN
        }
    }

    override fun bindViewModel() {
        viewModel.numberLiveData.nonNullObserve(viewLifecycleOwner) { data ->
            numberCardEditText.configure(data) {
                resolveState(it)
                resolveValidation(it)
            }
        }
    }

    private fun resolveState(textInput: String) {
        with(viewModel) {
            if (numberCardEditText.hasError()) {
                numberCardEditText.clearError()
            }

            updateInputData(CardFilledData.Number(textInput))
            onCardNumberChange(sanitizeBin(textInput))
        }
    }

    private fun sanitizeBin(textInput: String) = textInput.replace("\\s+".toRegex(), "")

    private fun resolveValidation(cardNumber: String) {
        with(viewModel) {
            isInputValid = when {
                !numberCardEditText.isComplete() -> {
                    false
                }

                !hasLuhnValidation() -> {
                    tracker.trackEvent(BinValidTrack())
                    numberCardEditText.addRightCheckDrawable(R.drawable.cf_icon_check)
                    true
                }

                else -> {
                    if (Luhn.isValid(cardNumber)) {
                        tracker.trackEvent(BinValidTrack())
                        numberCardEditText.addRightCheckDrawable(R.drawable.cf_icon_check)
                        true
                    } else {
                        tracker.trackEvent(BinInvalidTrack(numberCardEditText.getText()))
                        showError()
                        false
                    }
                }
            }
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        if (isInputValid) {
            with(viewModel) {
                tracker.trackEvent(NextTrack(TrackSteps.BIN_NUMBER.getType()))
                if (cardLiveData.value != null) {
                    move.invoke(position)
                    cardStepInfo.cardNumber = numberCardEditText.getText()
                } else {
                    retryFetchCard(context)
                }
            }
        } else {
            viewModel.tracker.trackEvent(BinInvalidTrack(numberCardEditText.getText()))
            showError()
        }
    }

    override fun toBack(position: Int, move: MoveTo) {
        super.toBack(position, move)
        viewModel.tracker.trackEvent(BackTrack(TrackSteps.BIN_NUMBER.getType()))
    }

    override fun trackFragmentView() {
        viewModel.tracker.trackView(BinNumberView())
    }

    override fun showError() {
        numberCardEditText.showError(if (!numberCardEditText.isComplete()) getString(R.string.cf_complete_field) else "")
    }

    companion object {
        private const val MIN_LENGTH_BIN = 6
    }
}
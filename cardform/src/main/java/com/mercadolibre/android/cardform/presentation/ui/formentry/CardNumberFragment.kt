package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.data.model.response.Validation
import com.mercadolibre.android.cardform.databinding.CfInputFormEdittextBinding
import com.mercadolibre.android.cardform.databinding.FragmentNumberCardBinding
import com.mercadolibre.android.cardform.presentation.delegate.viewBinding
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.postDelayed
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.helpers.KeyboardHelper
import com.mercadolibre.android.cardform.presentation.helpers.ValidationHelper
import com.mercadolibre.android.cardform.presentation.model.CardFilledData
import com.mercadolibre.android.cardform.presentation.model.ValidationType
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.bin.BinClearTrack
import com.mercadolibre.android.cardform.tracks.model.bin.BinInvalidTrack
import com.mercadolibre.android.cardform.tracks.model.bin.BinNumberView
import com.mercadolibre.android.cardform.tracks.model.bin.BinValidTrack
import com.mercadolibre.android.cardform.tracks.model.flow.BackTrack
import com.mercadolibre.android.cardform.tracks.model.flow.NextTrack

/**
 * A simple [Fragment] subclass.
 */
internal class CardNumberFragment : InputFragment() {

    private val fragmentNumberCardBinding by viewBinding(FragmentNumberCardBinding::bind)
    private lateinit var bindingEditText: CfInputFormEdittextBinding

    override val rootLayout = R.layout.fragment_number_card
    private var validations = listOf<Validation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(rootLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingEditText = CfInputFormEdittextBinding.bind(fragmentNumberCardBinding.root)
        if (savedInstanceState == null) {
            trackFragmentView()
            setDefaultConfiguration()
            postDelayed((resources.getInteger(R.integer.cf_anim_duration) * 1.5).toLong()) {
                if (isVisible) {
                    bindingEditText.input.requestFocus()
                    KeyboardHelper.showKeyboard(bindingEditText.input)
                }
            }
        }

        fragmentNumberCardBinding.numberCardEditText.addOnIconClickListener {
            viewModel.tracker.trackEvent(BinClearTrack())
        }

        val filter = InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                val char = source[i]
                if (!Character.isDigit(char) && !Character.isSpaceChar(char)) {
                    return@InputFilter ""
                }
            }
            null
        }

        fragmentNumberCardBinding.numberCardEditText.addFilters(arrayOf(filter))
    }

    private fun setDefaultConfiguration() {
        fragmentNumberCardBinding.numberCardEditText.configure(
            ObjectStepFactory.createDefaultStepFrom(
                resources,
                FormType.CARD_NUMBER.getType()
            )
        ) {
            resolveState(it)
            fragmentNumberCardBinding.numberCardEditText.clearError()
            isInputValid = sanitizeBin(it).length >= MIN_LENGTH_BIN
        }
    }

    override fun bindViewModel() {
        with(viewModel) {
            extraValidation.nonNullObserve(viewLifecycleOwner) {
                validations = it
            }
            numberLiveData.nonNullObserve(viewLifecycleOwner) { data ->
                fragmentNumberCardBinding.numberCardEditText.configure(data) {
                    resolveValidation()
                    resolveState(it)
                }
            }
        }
    }

    private fun resolveState(textInput: String) {
        with(viewModel) {
            updateInputData(CardFilledData.Number(textInput))
            onCardNumberChange(sanitizeBin(textInput))
        }
    }

    private fun sanitizeBin(textInput: String) = textInput.replace("\\s+".toRegex(), "")

    private fun resolveValidation() {
        with(viewModel) {
            val cardNumber = fragmentNumberCardBinding.numberCardEditText.getText()

            isInputValid = ValidationHelper
                .validateWith(ValidationType.ExtraValidation(cardNumber, validations),
                    blockValid = { fragmentNumberCardBinding.numberCardEditText.clearError() },
                    blockInvalid = {
                        tracker.trackEvent(BinInvalidTrack(cardNumber))
                        fragmentNumberCardBinding.numberCardEditText.showError(it)
                    })
                .validateWith(ValidationType.Complete(cardNumber, fragmentNumberCardBinding.numberCardEditText.getMaxLength()))
                .validateWith(ValidationType.Luhn(cardNumber, hasLuhnValidation()),
                    blockValid = {
                        fragmentNumberCardBinding.numberCardEditText.clearError()
                        fragmentNumberCardBinding.numberCardEditText.addRightCheckDrawable(R.drawable.cf_icon_check)
                        viewModel.trackValidBinNumber()
                    },
                    blockInvalid = {
                        tracker.trackEvent(BinInvalidTrack(cardNumber))
                        showError()
                    }).validate()
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        val cardNumber = fragmentNumberCardBinding.numberCardEditText.getText()
        if (isInputValid) {
            with(viewModel) {
                tracker.trackEvent(NextTrack(TrackSteps.BIN_NUMBER.getType()))
                if (cardLiveData.value != null) {
                    move.invoke(position)
                    cardStepInfo.cardNumber = cardNumber
                } else {
                    retryFetchCard(context)
                }
            }
        } else {
            viewModel.tracker.trackEvent(BinInvalidTrack(cardNumber))
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

    override fun getSharedViewModelScope() = parentFragment!!

    override fun showError() {
        fragmentNumberCardBinding.numberCardEditText.showError(if (!fragmentNumberCardBinding.numberCardEditText.isComplete()) getString(R.string.cf_complete_field) else "")
    }

    companion object {
        private const val MIN_LENGTH_BIN = 6
    }
}
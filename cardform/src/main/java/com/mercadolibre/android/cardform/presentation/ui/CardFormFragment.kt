package com.mercadolibre.android.cardform.presentation.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardDrawerView
import com.meli.android.carddrawer.model.CardUI
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.RootFragment
import com.mercadolibre.android.cardform.presentation.extensions.*
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.helpers.KeyboardHelper
import com.mercadolibre.android.cardform.presentation.model.*
import com.mercadolibre.android.cardform.presentation.ui.formentry.FormType
import com.mercadolibre.android.cardform.presentation.viewmodel.InputFormViewModel
import com.mercadolibre.android.cardform.presentation.model.StateUi.UiLoading
import com.mercadolibre.android.cardform.presentation.model.UiError
import com.mercadolibre.android.cardform.presentation.model.UiResult
import com.mercadolibre.android.cardform.presentation.ui.custom.ProgressFragment
import com.mercadolibre.android.cardform.tracks.model.flow.InitTrack
import com.mercadolibre.android.cardform.tracks.model.flow.SuccessTrack
import kotlinx.android.synthetic.main.cf_card.inputViewPager
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_card_form.*

/**
 * A simple [Fragment] subclass.
 * Use the [CardFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
internal class CardFormFragment : RootFragment<InputFormViewModel>() {

    override val viewModelClass = InputFormViewModel::class.java
    override val rootLayout = R.layout.fragment_card_form

    private var fromFragment = false
    private var requestCode = 0
    private lateinit var defaultCardDrawerConfiguration: CardUI
    private var animationEnded = false
    private var progressFragment: ProgressFragment? = null
    private lateinit var cardDrawer: CardDrawerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            fromFragment = getBoolean(ARG_FROM_FRAGMENT, false)
            requestCode = (getParcelable(ARG_CARD_FORM) as CardForm).requestCode
        }
        defaultCardDrawerConfiguration = object : DefaultCardConfiguration(context) {
            override fun getNamePlaceHolder(): String {
                return getString(R.string.cf_card_name_hint)
            }

            override fun getExpirationPlaceHolder(): String {
                return getString(R.string.cf_card_date_hint)
            }
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (fromFragment) {
            val duration = resources.getInteger(R.integer.cf_anim_duration).toLong()
            val offset = (duration * 0.5).toLong()
            if (enter) {
                if (!animationEnded) {
                    cardDrawer.pushUpIn(onFinish = {
                        animationEnded = true
                    })
                    buttonContainer.fadeIn()
                    inputViewPager.slideLeftIn(offset)
                    progress.slideRightIn(offset)
                    title.fadeIn(duration, offset)
                }
            } else {
                cardDrawer.pushDownOut()
                inputViewPager.slideRightOut()
                buttonContainer.goneDuringAnimation()
                progress.fadeOut()
                title.fadeOut()
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardDrawer = view.findViewById(R.id.cardDrawer)

        if (savedInstanceState == null) {
            viewModel.tracker.trackEvent(InitTrack())
            cardDrawer.show(object : DefaultCardConfiguration(context) {
                override fun getNamePlaceHolder(): String {
                    return getString(R.string.cf_card_name_hint)
                }

                override fun getExpirationPlaceHolder(): String {
                    return getString(R.string.cf_card_date_hint)
                }

                override fun getAnimationType(): String {
                    return CardAnimationType.NONE
                }
            })
        }

        FragmentNavigationController.init(childFragmentManager, inputViewPager)
        KeyboardHelper.addKeyBoardListener(this@CardFormFragment)

        animationEnded = savedInstanceState?.getBoolean(EXTRA_ANIMATION, false) ?: false

        if (!fromFragment) {
            KeyboardHelper.showKeyboard(this)
        }

        cardDrawer.hideSecCircle()
        enableBackButton()

        next.setOnClickListener {
            if (!FragmentNavigationController.toNext()) {
                viewModel.associateCard()
            }
            enableBackButton()
        }

        back.setOnClickListener {
            FragmentNavigationController.toBack()
            enableBackButton()
        }

        (activity as AppCompatActivity?)?.apply {
            appBar.configureToolbar(this)
            appBar.setOnBackListener {
                KeyboardHelper.hideKeyboard(this@CardFormFragment)
                postDelayed(100) {
                    onBackPressed()
                }
            }
        }
    }

    private fun enableBackButton() {
        with(back) {
            inputViewPager.post {
                isEnabled = inputViewPager.currentItem != 0
                setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        if (isEnabled) {
                            R.color.cf_button_navigation_text
                        } else {
                            R.color.card_drawer_gray_light
                        }
                    )
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_ANIMATION, animationEnded)
    }

    override fun bindViewModel() {
        with(viewModel) {
            cardLiveFilledData.nonNullObserve(viewLifecycleOwner) {
                when (it) {
                    is CardFilledData.Number -> {
                        cardDrawer.card.number = it.input
                    }
                    is CardFilledData.Name -> {
                        cardDrawer.card.name = it.input
                    }
                    is CardFilledData.ExpirationDate -> {
                        cardDrawer.card.expiration = it.input
                    }
                    is CardFilledData.Cvv -> {
                        cardDrawer.card.secCode = it.input
                    }
                }
            }

            progressLiveData.nonNullObserve(viewLifecycleOwner) {
                appBar.updateProgress(it)
            }

            cardLiveData.observe(viewLifecycleOwner, Observer { cardData ->
                var maxLengthBin = 0
                val cardDrawerData = if (cardData != null) {
                    FragmentNavigationController.setAdditionalSteps(cardData.additionalSteps)
                    appBar.setTitle(TitleBar.fromType(cardData.paymentTypeId).getTitle())
                    cardData.cardUi!!.let {
                        maxLengthBin = it.cardNumberLength
                        CardDrawerData(context!!, it)
                    }
                } else {
                    appBar.setTitle(TitleBar.NONE_TITLE.getTitle())
                    with(cardDrawer.card) {
                        secCode = ""
                        expiration = ""
                    }
                    defaultCardDrawerConfiguration
                }

                cardDrawer.show(cardDrawerData)
                numberLiveData.value = ObjectStepFactory
                    .createDefaultStepFrom(
                        resources,
                        FormType.CARD_NUMBER.getType(),
                        maxLengthBin,
                        cardDrawerData.cardNumberPattern
                    )
            })

            stateUiLiveData.nonNullObserve(viewLifecycleOwner) {
                when (it) {

                    is UiLoading -> {
                        showProgress()
                    }

                    is UiError -> {
                        resolveError(it)
                    }

                    is UiResult -> {
                        resolveResult(it)
                    }
                }
            }

            updateCardData.nonNullObserve(viewLifecycleOwner) {
                cardDrawer.update(CardDrawerData(context!!, it))
            }

            stateCardLiveData.nonNullObserve(viewLifecycleOwner) {
                when (it) {
                    CardState.ShowCode -> {
                        cardDrawer.showSecurityCode()
                    }

                    CardState.HideCode -> {
                        cardDrawer.show()
                    }
                }
            }
        }
    }

    private fun showProgress() {
        if (progressFragment == null) {
            progressFragment = ProgressFragment.newInstance()
        }
        progressFragment?.apply {
            if (!isVisible) {
                progressFragment?.showNow(
                    this@CardFormFragment.childFragmentManager,
                    ProgressFragment.TAG
                )
            }
        }
    }

    private fun hideProgress() {
        progressFragment?.apply {
            if (isVisible) {
                dismiss()
            }
        }
    }

    private fun resolveError(error: UiError) {
        hideProgress()
        if (error.showError) {
            ErrorUtil.resolveError(
                rootCardForm,
                error,
                if (error is UiError.ConnectionError) View.OnClickListener {
                    viewModel.retryFetchCard(context)
                } else {
                    null
                }
            )
        }
    }

    private fun resolveResult(result: UiResult) {
        hideProgress()
        if (result is UiResult.CardResult) {
            returnResult(result.data, Activity.RESULT_OK)
        }
    }

    private fun returnResult(data: String, resultCode: Int) {
        //Not sending any data currently, only ok or not
        activity?.apply {
            if (fromFragment) {
                supportFragmentManager.popBackStackImmediate()
                getCurrentFragment(supportFragmentManager)?.onActivityResult(
                    requestCode,
                    resultCode,
                    null
                )
            } else {
                setResult(resultCode)
                finish()
            }
            viewModel.tracker.trackEvent(SuccessTrack())
        }
    }

    private fun getCurrentFragment(manager: FragmentManager): Fragment? = try {
        manager.fragments.run {
            get(size - 1)
        }
    } catch (e: Exception) {
        null
    }

    companion object {
        private const val ARG_FROM_FRAGMENT = "from_fragment"
        private const val EXTRA_ANIMATION = "animation"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CardFormFragment.
         */

        fun newInstance(fromFragment: Boolean, cardForm: CardForm) = CardFormFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_FROM_FRAGMENT, fromFragment)
                putParcelable(ARG_CARD_FORM, cardForm)
            }
        }
    }
}
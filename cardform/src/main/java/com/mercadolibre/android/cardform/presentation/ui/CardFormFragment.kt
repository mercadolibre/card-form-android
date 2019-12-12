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
import com.meli.android.carddrawer.model.CardUI
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.RootFragment
import com.mercadolibre.android.cardform.presentation.extensions.*
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.*
import com.mercadolibre.android.cardform.presentation.ui.formentry.FormType
import com.mercadolibre.android.cardform.presentation.viewmodel.InputFormViewModel
import com.mercadolibre.android.cardform.presentation.model.StateUi.UiLoading
import com.mercadolibre.android.cardform.presentation.model.UiError
import com.mercadolibre.android.cardform.presentation.model.UiResult
import kotlinx.android.synthetic.main.fragment_card_form.*

/**
 * A simple [Fragment] subclass.
 * Use the [CardFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFormFragment : RootFragment<InputFormViewModel>() {

    override val viewModelClass = InputFormViewModel::class.java
    override val rootLayout = R.layout.fragment_card_form

    private var fromFragment = false
    private var requestCode = 0
    private lateinit var defaultCardDrawerConfiguration: CardUI
    private var animationEnded = false

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
            if (enter) {
                cardDrawer.pushUpIn()
                back.fadeIn()
                next.fadeIn()
                inputViewPager.slideInRight(onFinish = {
                    animationEnded = true
                    FragmentNavigationController.showKeyboard(this)
                })
            } else {
                cardDrawer.pushDownOut()
                back.fadeOut()
                next.fadeOut()
                inputViewPager.slideOutRight()
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            cardDrawer.show(defaultCardDrawerConfiguration)
        }

        FragmentNavigationController.init(childFragmentManager, inputViewPager)
        FragmentNavigationController.addKeyBoardListener(this@CardFormFragment)

        animationEnded = savedInstanceState?.getBoolean(EXTRA_ANIMATION, false) ?: false

        if (!fromFragment) {
            FragmentNavigationController.showKeyboard(this)
        }

        cardDrawer.hideSecCircle()
        enableBackButton()

        activity?.apply {
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

            appBar.configureToolbar(this as AppCompatActivity)
            appBar.setOnBackListener {
                onBackPressed()
            }
        }
    }

    private fun enableBackButton() {
        with(back) {
            isEnabled = inputViewPager.currentItem != 0
            setTextColor(
                ContextCompat.getColor(
                    context!!,
                    if (isEnabled) R.color.cf_button_navigation_text else R.color.card_drawer_gray_light
                )
            )
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

            cardLiveData.observe(viewLifecycleOwner, Observer {
                val cardDrawerData = if (it != null) {
                    FragmentNavigationController.setAdditionalSteps(it.additionalSteps)
                    appBar.setTitle(TitleBar.fromType(it.paymentTypeId).getTitle())
                    CardDrawerData(context!!, it.cardUi!!)
                } else {
                    appBar.setTitle(TitleBar.NONE_TITLE.getTitle())
                    defaultCardDrawerConfiguration
                }

                cardDrawer.show(cardDrawerData)
                numberLiveData.value = ObjectStepFactory
                    .createDefaultStepFrom(
                        resources,
                        FormType.CARD_NUMBER.getType(),
                        cardDrawerData.cardNumberPattern
                    )
            })

            stateUiLiveData.nonNullObserve(viewLifecycleOwner) {
                when (it) {

                    is UiLoading -> {
                        progressLoading.visible()
                    }

                    is UiError -> {
                        resolveError(it)
                    }

                    is UiResult -> {
                        resolveResult(it)
                    }
                }
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

    private fun resolveError(error: UiError) {
        progressLoading.gone()
        ErrorUtil.resolveError(rootCardForm, error)
    }

    private fun resolveResult(result: UiResult) {
        progressLoading.gone()
        if (result is UiResult.CardResult) {
            returnResult(result.data, Activity.RESULT_OK)
        }
    }

    private fun returnResult(data: String, resultCode: Int) {
        activity?.apply {
            val intent = Intent()
            intent.data = Uri.parse(data)
            if (fromFragment)
                getCurrentFragment(supportFragmentManager)?.let { fragment ->
                    fragment.onActivityResult(requestCode, resultCode, intent)
                    supportFragmentManager.popBackStack()
                } else {
                setResult(resultCode, intent)
                finish()
            }
        }
    }

    private fun getCurrentFragment(manager: FragmentManager): Fragment? {
        val fragmentTag = manager.getBackStackEntryAt(0).name
        return manager.findFragmentByTag(fragmentTag)
    }

    companion object {
        const val TAG = "card_form"
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
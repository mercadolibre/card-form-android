package com.mercadolibre.android.cardform.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.CardForm.Companion.RESULT_CARD_ID_KEY
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.viewModel
import com.mercadolibre.android.cardform.internal.CardFormWeb
import com.mercadolibre.android.cardform.presentation.extensions.*
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.utils.ViewUtils
import com.mercadolibre.android.cardform.presentation.viewmodel.webview.CardFormWebViewModel

private const val SUCCESS_RETURN_DELAY = 1000L

internal class CardFormWebActivity : AppCompatActivity() {

    private val viewModel: CardFormWebViewModel by viewModel()
    private lateinit var cardFormWebContainer: FrameLayout
    private lateinit var progressStateContainer: FrameLayout
    private lateinit var webViewContainer: FrameLayout
    private var defaultStatusBarColor: Int = 0
    private var canGoBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_form_web)
        cardFormWebContainer = findViewById(R.id.card_form_web)
        webViewContainer = findViewById(R.id.web_view_fragment_container)
        progressStateContainer = findViewById(R.id.progress_state_fragment_container)
        defaultStatusBarColor = window.statusBarColor

        intent.extras?.let { extras ->
            val cardFormData = extras.getParcelable<CardForm>(CARD_FORM_EXTRA)!!
            Dependencies.instance.initialize(this, cardFormData)
        } ?: error("Card form extra should not be null")

        with(viewModel) {
            if (savedInstanceState == null) {
                setUpScreenComponents()
                showProgressStartScreen()
                initInscription()
                trackInit()
            } else {
                recoverFromBundle(savedInstanceState)
            }
        }
        setUpViewModel()
    }

    private fun setUpViewModel() {
        with(viewModel) {
            screenStateLiveData.nonNullObserve(this@CardFormWebActivity) {
                when (it) {
                    ScreenState.ProgressState -> {
                        window?.changeStatusBarColor(defaultStatusBarColor)
                        progressStateContainer.visible()
                        webViewContainer.gone()
                    }
                    else -> {
                        window?.changeStatusBarColor(
                            ViewUtils.getDarkPrimaryColor(
                                ContextCompat.getColor(
                                    this@CardFormWebActivity,
                                    R.color.ui_components_android_color_primary
                                )
                            )
                        )
                        progressStateContainer.gone()
                        webViewContainer.visible()
                    }
                }
            }

            cardResultLiveData.nonNullObserve(this@CardFormWebActivity) { cardId ->
                setResult(Activity.RESULT_OK, Intent().putExtra(RESULT_CARD_ID_KEY, cardId))
                finishProcessAssociationCard()
            }

            finishAssociationCardLiveData.nonNullObserve(this@CardFormWebActivity) {
                showSuccessState()
                cardFormWebContainer.postDelayed({ finish() }, SUCCESS_RETURN_DELAY)
            }

            canGoBackViewLiveData.nonNullObserve(this@CardFormWebActivity) {
                canGoBack = it
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.storeInBundle(outState)
    }

    private fun setUpScreenComponents() {
        var pair = getStateFragment()
        updateScreenState(pair.first, progressStateContainer.id, pair.second)
        pair = getWebViewFragment()
        updateScreenState(pair.first, webViewContainer.id, pair.second)
    }

    private fun updateScreenState(fragment: Fragment, containerId: Int, fragmentTag: String) {
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction()
                .replace(
                    containerId,
                    fragment,
                    fragmentTag
                ).commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        if (canGoBack) {
            viewModel.trackBack()
            super.onBackPressed()
        }
    }

    private fun getStateFragment(): Pair<Fragment, String> {
        var fragment = supportFragmentManager.findFragmentByTag(CardFormWebViewStateFragment.TAG)
        fragment = fragment ?: let { CardFormWebViewStateFragment.newInstance() }

        return (fragment to CardFormWebViewStateFragment.TAG)
    }

    private fun getWebViewFragment(): Pair<Fragment, String> {
        var fragment = supportFragmentManager.findFragmentByTag(CardFormWebViewFragment.TAG)
        fragment = fragment ?: let { CardFormWebViewFragment.newInstance() }

        return (fragment to CardFormWebViewFragment.TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        Dependencies.instance.clean()
    }

    companion object {

        private fun getIntent(context: Context, cardFormWeb: CardFormWeb) =
            Intent(context, CardFormWebActivity::class.java).also { intent ->
                val bundle = Bundle().also { it.putParcelable(CARD_FORM_EXTRA, cardFormWeb) }
                intent.putExtras(bundle)
            }

        fun start(fragment: Fragment, requestCode: Int, cardFormWeb: CardFormWeb) {
            fragment.context?.let {
                fragment.startActivityForResult(getIntent(it, cardFormWeb), requestCode)
            }
        }

        fun start(activity: AppCompatActivity, requestCode: Int, cardFormWeb: CardFormWeb) {
            activity.startActivityForResult(getIntent(activity, cardFormWeb), requestCode)
        }
    }
}
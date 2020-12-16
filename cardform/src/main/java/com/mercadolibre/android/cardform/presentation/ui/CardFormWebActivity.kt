package com.mercadolibre.android.cardform.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.viewModel
import com.mercadolibre.android.cardform.presentation.extensions.*
import com.mercadolibre.android.cardform.presentation.extensions.gone
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.visible
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.viewmodel.webview.CardFormWebViewModel
import com.mercadolibre.android.cardform.internal.CardFormWeb
import com.mercadolibre.android.cardform.presentation.utils.ViewUtils

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defaultStatusBarColor = window.statusBarColor
        }
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

            canGoBackViewLiveData.nonNullObserve(this@CardFormWebActivity) {
                canGoBack = it
            }

            cardResultLiveData.nonNullObserve(this@CardFormWebActivity) { cardId ->
                showSuccessState()
                cardFormWebContainer.postDelayed({
                    val resultIntent = Intent().putExtra(CardForm.RESULT_CARD_ID_KEY, cardId)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }, 1000)
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

    companion object {
        fun start(activity: AppCompatActivity, requestCode: Int, cardFormWeb: CardFormWeb) {
            val intent = Intent(activity, CardFormWebActivity::class.java)
            val bundle = Bundle().also { it.putParcelable(CARD_FORM_EXTRA, cardFormWeb) }
            intent.putExtras(bundle)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
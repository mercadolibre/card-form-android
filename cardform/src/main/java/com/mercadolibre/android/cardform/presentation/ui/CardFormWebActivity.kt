package com.mercadolibre.android.cardform.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.viewModel
import com.mercadolibre.android.cardform.presentation.extensions.*
import com.mercadolibre.android.cardform.presentation.extensions.gone
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.visible
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.viewmodel.CardFormWebViewModel

private const val DARKEN_FACTOR = 0.1f

internal class CardFormWebActivity : AppCompatActivity() {

    private val viewModel: CardFormWebViewModel by viewModel()
    private lateinit var extras: Bundle
    private lateinit var progressStateContainer: FrameLayout
    private lateinit var webViewContainer: FrameLayout
    private var defaultStatusBarColor: Int = 0
    private var canGoBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_form_web)
        webViewContainer = findViewById(R.id.web_view_fragment_container)
        progressStateContainer = findViewById(R.id.progress_state_fragment_container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defaultStatusBarColor = window.statusBarColor
        }
        intent.extras?.let { extras ->
            this.extras = extras
            Dependencies.instance.initialize(
                this@CardFormWebActivity,
                extras.getParcelable(CARD_FORM_EXTRA)!!
            )

            check(extras.containsKey(USER_NAME_EXTRA)) { "User name should not be null" }
            check(extras.containsKey(USER_EMAIL_EXTRA)) { "User email should not be null" }
            check(extras.containsKey(RESPONSE_URL_EXTRA)) { "Response url should not be null" }
            setUpScreenComponents()
            viewModel.showProgressStartScreen()
            viewModel.initInscription(
                extras.getString(USER_NAME_EXTRA, ""),
                extras.getString(USER_EMAIL_EXTRA, ""),
                extras.getString(RESPONSE_URL_EXTRA, "")
            )
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
                            getDarkPrimaryColor(
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
        }
    }

    @ColorInt
    private fun getDarkPrimaryColor(@ColorInt primaryColor: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(primaryColor, hsv)
        hsv[1] = hsv[1] + DARKEN_FACTOR
        hsv[2] = hsv[2] - DARKEN_FACTOR
        return Color.HSVToColor(hsv)
    }

    private fun setUpScreenComponents() {
        var pair = getStateFragment()
        updateScreenState(pair.first, progressStateContainer.id, pair.second)
        pair = getWebViewFragment(extras)
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
            super.onBackPressed()
        }
    }

    private fun getStateFragment(): Pair<Fragment, String> {
        var fragment = supportFragmentManager.findFragmentByTag(CardFormWebViewStateFragment.TAG)
        fragment = fragment ?: let { CardFormWebViewStateFragment.newInstance() }

        return (fragment to CardFormWebViewStateFragment.TAG)
    }

    private fun getWebViewFragment(extras: Bundle): Pair<Fragment, String> {
        var fragment = supportFragmentManager.findFragmentByTag(CardFormWebViewFragment.TAG)
        fragment = fragment ?: let { CardFormWebViewFragment.newInstance(extras) }

        return (fragment to CardFormWebViewFragment.TAG)
    }

    companion object {
        fun start(activity: AppCompatActivity, requestCode: Int, bundle: Bundle) {
            val intent = Intent(activity, CardFormWebActivity::class.java)
            intent.putExtras(bundle)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
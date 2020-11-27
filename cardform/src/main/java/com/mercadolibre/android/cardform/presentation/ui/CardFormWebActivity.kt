package com.mercadolibre.android.cardform.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.viewModel
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.model.LoadingScreenState
import com.mercadolibre.android.cardform.presentation.viewmodel.CardFormWebViewModel

internal class CardFormWebActivity : AppCompatActivity() {

    private val viewModel: CardFormWebViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_form_web)

        intent.extras?.let { extras ->

            Dependencies.instance.initialize(
                this@CardFormWebActivity,
                extras.getParcelable(CARD_FORM_EXTRA)!!
            )

            check(extras.containsKey(USER_NAME_EXTRA)) { "User name should not be null" }
            check(extras.containsKey(USER_EMAIL_EXTRA)) { "User email should not be null" }
            check(extras.containsKey(RESPONSE_URL_EXTRA)) { "Response url should not be null" }

            viewModel.initInscription(
                extras.getString(USER_NAME_EXTRA, ""),
                extras.getString(USER_EMAIL_EXTRA, ""),
                extras.getString(RESPONSE_URL_EXTRA, "")
            )

            if (supportFragmentManager.findFragmentByTag(CardFormWebViewFragment.TAG) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.web_view_fragment_container,
                        CardFormWebViewFragment.newInstance(extras),
                        CardFormWebViewFragment.TAG
                    ).commitAllowingStateLoss()
            }
        }
    }


    private fun bindViewModel() {
        viewModel.loadingScreenStateLiveData.nonNullObserve(this) {

            when(it) {
                LoadingScreenState.Loading -> {
                    val (fragment, tag) = getStateFragment()
                    updateScreenState(fragment, tag)
                }
                else -> {
                    val (fragment, tag) = getWebViewFragment()
                    updateScreenState(fragment, tag)
                }
            }
        }
    }

    private fun updateScreenState(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.web_view_fragment_container,
                fragment,
                fragmentTag
            ).commitAllowingStateLoss()
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
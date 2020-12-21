package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.BaseFragment
import com.mercadolibre.android.cardform.di.sharedViewModel
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.postDelayed
import com.mercadolibre.android.cardform.presentation.model.WebViewData
import com.mercadolibre.android.cardform.presentation.viewmodel.webview.CardFormWebViewModel
import java.net.URI

private const val WEB_VIEW_DATA_EXTRA = "web_view_data"

internal class CardFormWebViewFragment : BaseFragment<CardFormWebViewModel>() {
    override val rootLayout = R.layout.fragment_web_view
    override val viewModel: CardFormWebViewModel by sharedViewModel { activity!! }

    private lateinit var webView: WebView
    private lateinit var appBarWebView: Toolbar
    private var webViewData: WebViewData? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.web_view)
        appBarWebView = view.findViewById(R.id.app_bar_web_view)

        savedInstanceState?.let { bundle ->
            webViewData = bundle.getParcelable(WEB_VIEW_DATA_EXTRA) as WebViewData?
            setUpWebView()
        }

        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(appBarWebView)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setHomeButtonEnabled(true)
            }
            appBarWebView.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun bindViewModel() {
        viewModel.loadWebViewLiveData.nonNullObserve(viewLifecycleOwner) { webData ->
            webViewData = webData
            setUpWebView()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webViewData?.also {
            val (redirectUrl, webUrl, tokenData) = it
            val webViewClient = CardFormWebViewClient()
            webView.settings.also { settings ->
                settings.javaScriptEnabled = true
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
                } else {
                    CookieManager.getInstance().setAcceptCookie(true)
                }
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
            webView.webViewClient = webViewClient

            webViewClient.addCardFormWebViewListener(object : CardFormWebViewListener {
                val uriWebUrl = URI(webUrl).path
                override fun onPageFinished(url: String?) {
                    if (URI(url).path == uriWebUrl) {
                        viewModel.showSuccessState()
                        postDelayed(1000) { viewModel.showWebViewScreen() }
                    }

                    if (url == redirectUrl) {
                        viewModel.showProgressBackScreen()
                        viewModel.finishInscription()
                        webViewData = null
                    }
                }
            })

            webView.postUrl(webUrl, tokenData)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(WEB_VIEW_DATA_EXTRA, webViewData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (android.os.Build.VERSION.SDK_INT >= 21)
            CookieManager.getInstance().removeAllCookies(null);
        else
            CookieManager.getInstance().removeAllCookie();
        webView.clearCache(true);
    }

    companion object {
        const val TAG = "web_view"
        fun newInstance() = CardFormWebViewFragment()
    }
}
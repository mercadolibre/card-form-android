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
import com.mercadolibre.android.cardform.presentation.viewmodel.webview.CardFormWebViewModel

private const val WEB_VIEW_DATA_EXTRA = "web_view_data"

internal class CardFormWebViewFragment : BaseFragment<CardFormWebViewModel>() {
    override val rootLayout = R.layout.fragment_web_view
    override val viewModel: CardFormWebViewModel by sharedViewModel { activity!! }

    private lateinit var webView: WebView
    private lateinit var appBarWebView: Toolbar
    private var webViewData: Triple<String, String, ByteArray>? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.web_view)
        appBarWebView = view.findViewById(R.id.app_bar_web_view)

        WebView.setWebContentsDebuggingEnabled(true)

        savedInstanceState?.let { bundle ->
            webViewData =
                bundle.getSerializable(WEB_VIEW_DATA_EXTRA) as Triple<String, String, ByteArray>?
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
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
            }
            webView.webViewClient = webViewClient

            webViewClient.addCardFormWebViewListener(object : CardFormWebViewListener {
                override fun onPageFinished(url: String?) {
                    if (url == webUrl) {
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
        outState.putSerializable(WEB_VIEW_DATA_EXTRA, webViewData)
    }

    companion object {
        const val TAG = "web_view"
        fun newInstance() = CardFormWebViewFragment()
    }
}
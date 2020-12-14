package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.RootFragment
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import com.mercadolibre.android.cardform.presentation.viewmodel.CardFormWebViewModel

internal class CardFormWebViewFragment : RootFragment<CardFormWebViewModel>() {
    override val viewModelClass = CardFormWebViewModel::class.java
    override val rootLayout = R.layout.fragment_web_view

    private lateinit var webView: WebView
    private lateinit var webViewClient: CardFormWebViewClient
    private lateinit var iconInclude: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.web_view)
        iconInclude = view.findViewById(R.id.progress_include)
        viewModel.initInscription()
    }

    override fun bindViewModel() {
        viewModel.webUiStateLiveData.nonNullObserve(this) {
            when (it) {
                is WebUiState.WebSuccess -> {
                    configureWebView(it.redirectUrl)
                    webView.postUrl(it.url, it.token)
                }

                WebUiState.WebProgress -> {
                }

                WebUiState.WebError -> {
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(redirectUrl: String) {
        webView.settings.javaScriptEnabled = true
        webViewClient = CardFormWebViewClient()
        webView.webViewClient = webViewClient
        webViewClient.addCardFormWebViewListener(object : CardFormWebViewListener {
            override fun onPageFinished() {
                context?.let {
                    val scriptInputStream = it.assets.open("override.js")
                    webView.evaluateJavascript(scriptInputStream.reader().readText(), null)
                }
            }

            override fun onReceivingData(data: String) {
                viewModel.finishInscription(data)
            }
        })
        val recorder = PayloadRecorder(webViewClient, redirectUrl)
        webView.addJavascriptInterface(recorder, "recorder")
        webView.visibility = View.VISIBLE
        iconInclude.visibility = View.GONE
    }

    companion object {
        const val TAG = "web_view"
        fun newInstance(bundle: Bundle) = CardFormWebViewFragment().also {
            it.arguments = bundle
        }
    }
}
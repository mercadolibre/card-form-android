package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.RootFragment
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import com.mercadolibre.android.cardform.presentation.viewmodel.CardFormWebViewModel

internal const val USER_NAME_EXTRA = "user_name"
internal const val USER_EMAIL_EXTRA = "user_email"
internal const val RESPONSE_URL_EXTRA = "response_url"

@SuppressLint("SetJavaScriptEnabled")
internal class CardFormWebViewFragment: RootFragment<CardFormWebViewModel>() {
    override val viewModelClass = CardFormWebViewModel::class.java
    override val rootLayout = R.layout.fragment_web_view

    private lateinit var webView: WebView
    private lateinit var iconInclude: View

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.web_view)
        WebView.setWebContentsDebuggingEnabled(true)

        webView.settings.javaScriptEnabled = true
        val webViewClient = CardFormWebViewClient()
        val recorder = PayloadRecorder(webViewClient, "https://www.comercio.cl/return_inscription")
        webView.addJavascriptInterface(recorder, "recorder")
        webView.webViewClient = webViewClient

        webViewClient.addCardFormWebViewListener(object : CardFormWebViewListener {
            override fun onPageFinished() {
                Log.i("JORGE", "onPageFinished")
                context?.let {
                    val scriptInputStream = it.assets.open("override.js")
                    webView.evaluateJavascript(scriptInputStream.reader().readText(), null)
                }
            }

            override fun onPageStarted() {
                Log.i("JORGE", "onPageStarted")
            }

            override fun onReceivingData(data: String) {
                Log.i("JORGE", "TOKEN_DATA: $data")
                viewModel.finishInscription(data)
            }
        })

        iconInclude = view.findViewById(R.id.progress_include)

        arguments?.let {
            check(it.containsKey(USER_NAME_EXTRA)) { "User name should not be null" }
            check(it.containsKey(USER_EMAIL_EXTRA)) { "User email should not be null" }
            check(it.containsKey(RESPONSE_URL_EXTRA)) { "Response url should not be null" }

            viewModel.initInscription(
                it.getString(USER_NAME_EXTRA, ""),
                it.getString(USER_EMAIL_EXTRA, ""),
                it.getString(RESPONSE_URL_EXTRA, "")
            )
        }
    }

    override fun bindViewModel() {
        viewModel.webUiStateLiveData.nonNullObserve(this) {
            when(it) {
                is WebUiState.WebSuccess -> {
                    Log.d("JORGE", "Success")

                    webView.visibility = View.VISIBLE
                    iconInclude.visibility = View.GONE
                    webView.postUrl(it.url, it.token)
                }

                WebUiState.WebProgress -> {
                    Log.d("JORGE", "Progress")
                }

                WebUiState.WebError -> {
                    Log.d("JORGE", "Error")
                }
            }
        }
    }

    companion object {
        const val TAG = "web_view"
        fun newInstance(bundle: Bundle) = CardFormWebViewFragment().apply {
            arguments = bundle
        }
    }
}
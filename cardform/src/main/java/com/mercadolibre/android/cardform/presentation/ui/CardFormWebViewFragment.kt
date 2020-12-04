package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.BaseFragment
import com.mercadolibre.android.cardform.di.sharedViewModel
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.postDelayed
import com.mercadolibre.android.cardform.presentation.viewmodel.CardFormWebViewModel

internal class CardFormWebViewFragment : BaseFragment<CardFormWebViewModel>() {
    override val rootLayout = R.layout.fragment_web_view
    override val viewModel: CardFormWebViewModel by sharedViewModel { activity!! }

    private lateinit var webView: WebView
    private lateinit var webViewClient: CardFormWebViewClient
    private lateinit var urlWebView: String
    private lateinit var appBarWebView: Toolbar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appBarWebView = view.findViewById(R.id.app_bar_web_view)
        (activity as AppCompatActivity?)?.apply {
            setSupportActionBar(appBarWebView)
            supportActionBar?.apply{
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setHomeButtonEnabled(true)
            }
            appBarWebView.setNavigationOnClickListener { onBackPressed() }
        }

        webView = view.findViewById(R.id.web_view)
        webView.settings.javaScriptEnabled = true
        val webViewClient = CardFormWebViewClient()
        val recorder = PayloadRecorder(webViewClient, "https://www.comercio.cl/return_inscription")
        webView.addJavascriptInterface(recorder, "recorder")
        webView.webViewClient = webViewClient

        webViewClient.addCardFormWebViewListener(object : CardFormWebViewListener {
            override fun onPageFinished(url: String?) {
                context?.let {
                    val scriptInputStream = it.assets.open("override.js")
                    webView.evaluateJavascript(scriptInputStream.reader().readText(), null)
                }

                if (url == urlWebView) {
                    viewModel.showSuccessState()
                    postDelayed(1000) { viewModel.showWebViewScreen() }
                }
            }

            override fun onReceivingData(data: String) {
                viewModel.showProgressBackScreen()
                viewModel.finishInscription(data)
            }
        })
    }

    override fun bindViewModel() {
        viewModel.loadWebViewLiveData.nonNullObserve(this) {
            urlWebView = it.first
            webView.postUrl(urlWebView, it.second)
        }
    }

    companion object {
        const val TAG = "web_view"
        fun newInstance(bundle: Bundle) = CardFormWebViewFragment().apply {
            arguments = bundle
        }
    }
}
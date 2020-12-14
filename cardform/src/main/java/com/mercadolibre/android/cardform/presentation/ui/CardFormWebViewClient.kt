package com.mercadolibre.android.cardform.presentation.ui

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient

internal class CardFormWebViewClient: WebViewClient() {

    private var webViewListener: CardFormWebViewListener? = null

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        webViewListener?.onPageStarted()
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        webViewListener?.onPageFinished()
        super.onPageFinished(view, url)
    }

    fun collectData(data: String) {
        webViewListener?.onReceivingData(data)
        webViewListener = null
    }

    fun addCardFormWebViewListener(webViewListener: CardFormWebViewListener) {
        this.webViewListener = webViewListener
    }
}

interface CardFormWebViewListener {
    fun onReceivingData(data: String) { }
    fun onPageFinished() { }
    fun onPageStarted() { }
}
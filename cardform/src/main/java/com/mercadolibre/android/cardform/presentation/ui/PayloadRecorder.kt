package com.mercadolibre.android.cardform.presentation.ui

import android.webkit.JavascriptInterface

private const val KEY_TOKEN = "TBK_TOKEN"

internal class PayloadRecorder(
    private val webViewClient: CardFormWebViewClient,
    private val urlAction: String) {

    @JavascriptInterface
    fun processFormData(data: String) {
        webViewClient.collectData(data)
    }

    @JavascriptInterface
    fun getUrlAction() = urlAction

    @JavascriptInterface
    fun getKeyData() = KEY_TOKEN
}
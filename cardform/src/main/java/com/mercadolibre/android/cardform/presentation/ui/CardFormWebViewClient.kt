package com.mercadolibre.android.cardform.presentation.ui

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import com.mercadolibre.android.cardform.base.orIfEmpty

internal class CardFormWebViewClient: WebViewClient() {

    private var webViewListener: CardFormWebViewListener = object : CardFormWebViewListener {}
    private var dataCollected: String = ""

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        webViewListener.onPageStarted(url)
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        webViewListener.onPageFinished(url)
        super.onPageFinished(view, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        val (url, errorMessage) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            request?.url?.path to error?.description.orIfEmpty("unknown error")
        } else {
            null to "unknown error"
        }
        webViewListener.onPageError(url, errorMessage)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        val (url, errorMessage) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            request?.url?.path to errorResponse?.reasonPhrase.orIfEmpty("unknown error")
        } else {
            null to "unknown error"
        }

        webViewListener.onPageError(url, errorMessage)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        webViewListener.onPageError(error?.url, error?.toString().orEmpty())
    }

    fun collectData(data: String) {
        if(dataCollected != data) {
            dataCollected = data
            webViewListener.onReceivingData(dataCollected)
        }
    }

    fun addCardFormWebViewListener(webViewListener: CardFormWebViewListener) {
        this.webViewListener = webViewListener
    }
}

internal interface CardFormWebViewListener {
    fun onReceivingData(data: String) { }
    fun onPageFinished(url: String?) { }
    fun onPageStarted(url: String?) { }
    fun onPageError(url: String?, errorMessage: String) { }
}
package com.mercadolibre.android.cardform.presentation.viewmodel.webview

internal object FlowRetryProvider {

    var retry: () -> Unit = {}
    private set

    fun setRetryFunction(block: () -> Unit) {
        retry = block
    }
}
package com.mercadolibre.android.cardform.presentation.model

sealed class LoadingScreenState {
    object Loading : LoadingScreenState()
    object WebView : LoadingScreenState()
}
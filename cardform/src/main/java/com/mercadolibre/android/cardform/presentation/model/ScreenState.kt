package com.mercadolibre.android.cardform.presentation.model

sealed class ScreenState {
    object ProgressState : ScreenState()
    object WebViewState : ScreenState()
}
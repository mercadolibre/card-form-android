package com.mercadolibre.android.cardform.presentation.model

sealed class StateUi {
    object Loading: StateUi()
    object Error: StateUi()
    object Result: StateUi()
}
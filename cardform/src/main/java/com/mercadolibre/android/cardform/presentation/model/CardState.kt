package com.mercadolibre.android.cardform.presentation.model

sealed class CardState {
    object ShowCode: CardState()
    object HideCode: CardState()
}
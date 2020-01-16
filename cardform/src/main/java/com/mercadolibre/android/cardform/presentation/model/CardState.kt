package com.mercadolibre.android.cardform.presentation.model

internal sealed class CardState {
    object ShowCode: CardState()
    object HideCode: CardState()
}
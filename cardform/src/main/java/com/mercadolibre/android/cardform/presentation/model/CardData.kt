package com.mercadolibre.android.cardform.presentation.model

sealed class CardData {
    class CardNumber(val input:String): CardData()
    class CardName(val input:String): CardData()
    class CardExpiration(val input:String): CardData()
    class CardEscCode(val input:String): CardData()
}
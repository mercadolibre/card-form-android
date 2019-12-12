package com.mercadolibre.android.cardform.presentation.model

sealed class CardFilledData {
    class Number(val input: String): CardFilledData()
    class Name(val input: String): CardFilledData()
    class ExpirationDate(val input: String): CardFilledData()
    class Cvv(val input: String): CardFilledData()
}
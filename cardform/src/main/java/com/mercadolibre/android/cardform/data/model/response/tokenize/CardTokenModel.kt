package com.mercadolibre.android.cardform.data.model.response.tokenize

data class CardTokenModel(
    val id: String,
    val esc: String,
    val lastFourDigits: String
)
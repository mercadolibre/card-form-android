package com.mercadolibre.android.cardform.data.model.body

data class CardInfoBody(
    val cardNumber: String,
    val cardholder: CardHolder,
    val expirationMonth: Int,
    val expirationYear: Int,
    val securityCode: String
)
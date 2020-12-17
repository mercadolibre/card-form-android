package com.mercadolibre.android.cardform.data.model.body

internal data class WebCardTokenBody(
    val cardNumberId: String,
    val truncCardNumber: String,
    val siteId: String,
    val cardholder: CardHolder,
    val expirationMonth: Int,
    val expirationYear: Int,
    val cardNumberLength: Int,
    val publicKey: String? = null
)
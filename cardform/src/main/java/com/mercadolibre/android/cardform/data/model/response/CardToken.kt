package com.mercadolibre.android.cardform.data.model.response

import com.mercadolibre.android.cardform.data.model.body.CardHolder

internal data class CardToken(
    val cardNumberLength: Int,
    val cardholder: CardHolder,
    val dateCreated: String,
    val dateDue: String,
    val dateLastUpdated: String,
    val expirationMonth: Int,
    val expirationYear: Int,
    val firstSixDigits: String,
    val id: String,
    val lastFourDigits: String,
    val liveMode: Boolean,
    val luhnValidation: Boolean,
    val publicKey: String,
    val requireEsc: Boolean,
    val securityCodeLength: Int,
    val status: String,
    val esc: String
)
package com.mercadolibre.android.cardform.data.model.body

import com.google.gson.annotations.SerializedName
import com.mercadolibre.android.cardform.data.model.esc.Device

internal data class CardInfoBody(
    val cardNumber: String,
    @SerializedName("cardholder") val cardHolder: CardHolder,
    val expirationMonth: Int,
    val expirationYear: Int,
    val securityCode: String,
    val device: Device,
    val requireEsc: Boolean = true
)
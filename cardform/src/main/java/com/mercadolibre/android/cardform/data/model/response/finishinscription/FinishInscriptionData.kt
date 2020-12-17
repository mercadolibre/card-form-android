package com.mercadolibre.android.cardform.data.model.response.finishinscription

import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody

internal data class FinishInscriptionData(
    val id: String,
    val firstSixDigits: String,
    val number: String,
    val expirationYear: Int,
    val expirationMonth: Int,
    val length: Int,
    val issuer: Issuer,
    val paymentMethod: PaymentMethodBody
)
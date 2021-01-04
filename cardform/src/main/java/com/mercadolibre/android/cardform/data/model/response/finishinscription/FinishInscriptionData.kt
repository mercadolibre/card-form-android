package com.mercadolibre.android.cardform.data.model.response.finishinscription

import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody

internal data class FinishInscriptionData(
    val cardTokenId: String,
    val firstSixDigits: String,
    val issuer: Issuer,
    val paymentMethod: PaymentMethodBody
)
package com.mercadolibre.android.cardform.data.model.response.finishinscription

data class FinishInscriptionBusinessModel(
    val cardTokenId: String,
    val firstSixDigits: String,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
)
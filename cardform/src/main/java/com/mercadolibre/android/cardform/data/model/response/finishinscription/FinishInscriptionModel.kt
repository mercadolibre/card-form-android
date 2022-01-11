package com.mercadolibre.android.cardform.data.model.response.finishinscription

data class FinishInscriptionModel(
    val cardTokenId: String,
    val bin: String,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
)
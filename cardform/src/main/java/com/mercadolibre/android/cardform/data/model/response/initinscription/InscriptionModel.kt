package com.mercadolibre.android.cardform.data.model.response.initinscription

data class InscriptionModel(
    val token: String,
    val urlWebPay: String,
    val redirectUrl: String,
    val fullName: String,
    val identifierNumber: String?,
    val identifierType: String?
)
package com.mercadolibre.android.cardform.data.model.response.initinscription

data class InscriptionBusinessModel(
    val token: String,
    val urlWebPay: String,
    val redirectUrl: String,
    val userName: String,
    val userLastName: String,
    val identifierNumber: String?,
    val identifierType: String?
)
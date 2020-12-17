package com.mercadolibre.android.cardform.data.model.response.initinscription

import com.google.gson.annotations.SerializedName

internal data class InscriptionDataModel(
    val tbkToken: String,
    @SerializedName("url_webpay")
    val urlWebPay: String,
    val redirectUrl: String,
    val user: User
)
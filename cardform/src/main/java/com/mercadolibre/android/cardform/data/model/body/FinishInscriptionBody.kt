package com.mercadolibre.android.cardform.data.model.body

import com.google.gson.annotations.SerializedName

internal data class FinishInscriptionBody(
    val siteId: String,
    @SerializedName("token")
    val tbkToken: String,
    @SerializedName("cardholder")
    val cardHolder: CardHolder
)
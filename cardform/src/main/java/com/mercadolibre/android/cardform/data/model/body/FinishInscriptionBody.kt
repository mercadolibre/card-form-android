package com.mercadolibre.android.cardform.data.model.body

import com.google.gson.annotations.SerializedName

internal data class FinishInscriptionBody(
    val siteId: String,
    val token: String,
    @SerializedName("cardholder")
    val cardHolder: CardHolder
)
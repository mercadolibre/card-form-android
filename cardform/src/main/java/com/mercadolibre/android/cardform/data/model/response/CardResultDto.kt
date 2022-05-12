package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcelable
import com.mercadopago.android.px.addons.tokenization.TokenizationResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardResultDto(
    val cardId: String,
    val bin: String,
    val paymentType: String,
    val lastFourDigits: String,
    val tokenizationResponse: TokenizationResponse? = null
) : Parcelable

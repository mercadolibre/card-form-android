package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardResultDto(
    val cardId: String,
    val bin: String,
    val paymentType: String,
    val lastFourDigits: String
) : Parcelable
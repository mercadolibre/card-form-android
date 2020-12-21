package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenizeAssociationModel(
    val cardTokenId: String,
    val bin: String,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
): Parcelable
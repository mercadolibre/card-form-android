package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenizeAssociationModel(
    val cardNumberId: String,
    val truncCardNumber: String,
    val expirationMonth: Int,
    val expirationYear: Int,
    val cardNumberLength: Int,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
): Parcelable
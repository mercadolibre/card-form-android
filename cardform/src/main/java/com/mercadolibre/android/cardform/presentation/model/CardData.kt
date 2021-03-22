package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcelable
import com.mercadolibre.android.cardform.data.model.response.CardUi
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class CardData(
    val formTitle: String,
    val cardUi: CardUi?,
    val paymentTypeId: String,
    val name: String,
    val issuerName: String?,
    val additionalSteps: List<String>) : Parcelable
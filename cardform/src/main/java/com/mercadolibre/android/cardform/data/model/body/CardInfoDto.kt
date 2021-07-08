package com.mercadolibre.android.cardform.data.model.body

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardInfoDto(
    val flowId: String,
    val vertical: String,
    val flowType: String,
    var bin: String,
    val callerId: String,
    val clientId: String,
    val siteId: String,
    val odr: Boolean,
    val items: List<ItemDto>
) : Parcelable

@Parcelize
data class ItemDto(
    val id: String
) : Parcelable

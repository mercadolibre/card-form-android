package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class WebViewData(
    val redirectUrl: String,
    val webUrl: String,
    val tokenData:ByteArray
): Parcelable
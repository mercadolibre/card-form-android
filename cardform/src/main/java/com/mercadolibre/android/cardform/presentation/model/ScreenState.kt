package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class ScreenState: Parcelable {
    @Parcelize
    object ProgressState : ScreenState()
    @Parcelize
    object WebViewState : ScreenState()
}
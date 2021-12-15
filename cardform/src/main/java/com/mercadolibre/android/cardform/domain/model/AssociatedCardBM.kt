package com.mercadolibre.android.cardform.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class AssociatedCardBM(
    val id: String,
    val enrollmentSuggested: Boolean
) : Parcelable

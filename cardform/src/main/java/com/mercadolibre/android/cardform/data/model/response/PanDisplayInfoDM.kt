package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents the PAN style to show on the card
 */
internal data class PanDisplayInfoDM(
    val backgroundColor: String? = null,
    val textColor: String? = null,
    val weight: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backgroundColor)
        parcel.writeString(textColor)
        parcel.writeString(weight)
    }

    companion object CREATOR : Parcelable.Creator<PanDisplayInfoDM> {
        override fun createFromParcel(parcel: Parcel): PanDisplayInfoDM = PanDisplayInfoDM(parcel)
        override fun newArray(size: Int): Array<PanDisplayInfoDM?> = arrayOfNulls(size)
    }
}
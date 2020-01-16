package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcel
import android.os.Parcelable

internal data class Identification(
    val id: String,
    val name: String,
    val type: String,
    val minLength: Int,
    val maxLength: Int,
    val mask: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun toString() = name

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeInt(minLength)
        parcel.writeInt(maxLength)
        parcel.writeString(mask)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Identification> {
        override fun createFromParcel(parcel: Parcel) = Identification(parcel)
        override fun newArray(size: Int)=  arrayOfNulls<Identification?>(size)
    }
}
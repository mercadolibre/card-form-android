package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class Issuer (
    val name : String,
    val id : Int,
    val imageUrl : String?,
    val isDefault : Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(imageUrl)
        parcel.writeByte(if (isDefault) 1 else 0)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Issuer> {
        override fun createFromParcel(parcel: Parcel) = Issuer(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Issuer?>(size)
    }
}
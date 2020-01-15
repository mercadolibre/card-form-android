package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class CardUi(
    val cardNumberLength: Int,
    val cardColor: String,
    val cardFontColor: String,
    val cardFontType: String,
    val securityCodeLocation: String,
    val securityCodeLength: Int,
    val paymentMethodImageUrl: String?,
    var issuerImageUrl: String?,
    var cardPattern: IntArray,
    val validation: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createIntArray()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cardNumberLength)
        parcel.writeString(cardColor)
        parcel.writeString(cardFontColor)
        parcel.writeString(cardFontType)
        parcel.writeString(securityCodeLocation)
        parcel.writeInt(securityCodeLength)
        parcel.writeString(paymentMethodImageUrl)
        parcel.writeString(issuerImageUrl)
        parcel.writeIntArray(cardPattern)
        parcel.writeString(validation)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CardUi> {
        override fun createFromParcel(parcel: Parcel) = CardUi(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardUi?>(size)
    }
}
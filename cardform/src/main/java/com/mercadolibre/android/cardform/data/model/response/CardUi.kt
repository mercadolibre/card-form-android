package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcel
import android.os.Parcelable

internal data class CardUi (
    val cardNumberLength : Int,
    val cardColor : String,
    val cardFontColor : String,
    val cardFontType : String,
    val securityCodeLocation : String,
    val securityCodeLength : Int,
    val paymentMethodImageUrl : String?,
    var issuerImageUrl : String?,
    var cardPattern : IntArray
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
        parcel.createIntArray()!!
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
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardUi> {
        override fun createFromParcel(parcel: Parcel): CardUi {
            return CardUi(parcel)
        }

        override fun newArray(size: Int): Array<CardUi?> {
            return arrayOfNulls(size)
        }
    }
}
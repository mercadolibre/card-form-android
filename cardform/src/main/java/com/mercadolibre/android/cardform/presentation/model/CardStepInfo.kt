package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class CardStepInfo(
    var cardNumber: String = "",
    var nameOwner: String = "",
    var expiration: String = "",
    var code: String = "",
    var identificationId: String = "",
    var identificationNumber: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardNumber)
        parcel.writeString(nameOwner)
        parcel.writeString(expiration)
        parcel.writeString(code)
        parcel.writeString(identificationId)
        parcel.writeString(identificationNumber)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CardStepInfo> {
        override fun createFromParcel(parcel: Parcel) = CardStepInfo(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardStepInfo?>(size)
    }
}
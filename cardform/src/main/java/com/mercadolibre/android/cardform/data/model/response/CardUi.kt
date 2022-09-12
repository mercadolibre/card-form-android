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
    var cardPattern : IntArray,
    val panStyle: PanDisplayInfoDM?,
    val validation: String,
    val extraValidations: ArrayList<Validation>
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
        parcel.readParcelable(PanDisplayInfoDM::class.java.classLoader),
        parcel.readString()!!,
        parcel.createTypedArrayList(Validation)!!
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
        parcel.writeParcelable(panStyle, flags)
        parcel.writeString(validation)
        parcel.writeTypedList(extraValidations)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CardUi> {
        override fun createFromParcel(parcel: Parcel) = CardUi(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardUi?>(size)
    }
}
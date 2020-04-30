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
        parcel.writeString(validation)
        parcel.writeTypedList(extraValidations)
    }

    override fun describeContents() = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CardUi

        if (cardNumberLength != other.cardNumberLength) return false
        if (cardColor != other.cardColor) return false
        if (cardFontColor != other.cardFontColor) return false
        if (cardFontType != other.cardFontType) return false
        if (securityCodeLocation != other.securityCodeLocation) return false
        if (securityCodeLength != other.securityCodeLength) return false
        if (paymentMethodImageUrl != other.paymentMethodImageUrl) return false
        if (issuerImageUrl != other.issuerImageUrl) return false
        if (!cardPattern.contentEquals(other.cardPattern)) return false
        if (validation != other.validation) return false
        if (extraValidations != other.extraValidations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cardNumberLength
        result = 31 * result + cardColor.hashCode()
        result = 31 * result + cardFontColor.hashCode()
        result = 31 * result + cardFontType.hashCode()
        result = 31 * result + securityCodeLocation.hashCode()
        result = 31 * result + securityCodeLength
        result = 31 * result + (paymentMethodImageUrl?.hashCode() ?: 0)
        result = 31 * result + (issuerImageUrl?.hashCode() ?: 0)
        result = 31 * result + cardPattern.contentHashCode()
        result = 31 * result + validation.hashCode()
        result = 31 * result + extraValidations.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<CardUi> {
        override fun createFromParcel(parcel: Parcel) = CardUi(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardUi?>(size)
    }
}
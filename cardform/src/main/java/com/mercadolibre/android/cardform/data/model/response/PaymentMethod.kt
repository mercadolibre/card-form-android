package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcel
import android.os.Parcelable

internal data class PaymentMethod(
    val paymentMethodId: String,
    val paymentTypeId: String,
    val name: String,
    val processingModes: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(paymentMethodId)
        parcel.writeString(paymentTypeId)
        parcel.writeString(name)
        parcel.writeStringList(processingModes)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<PaymentMethod> {
        override fun createFromParcel(parcel: Parcel) = PaymentMethod(parcel)
        override fun newArray(size: Int) = arrayOfNulls<PaymentMethod?>(size)
    }
}
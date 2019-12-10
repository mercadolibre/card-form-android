package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcel
import android.os.Parcelable
import com.mercadolibre.android.cardform.data.model.response.CardUi

data class CardData(val cardUi: CardUi?,
                    val paymentTypeId: String,
                    val additionalSteps: List<String>): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(CardUi::class.java.classLoader),
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(cardUi, flags)
        parcel.writeString(paymentTypeId)
        parcel.writeStringList(additionalSteps)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CardData> {
        override fun createFromParcel(parcel: Parcel) = CardData(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardData?>(size)
    }
}
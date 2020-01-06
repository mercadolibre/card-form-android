package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcel
import android.os.Parcelable

class StepData(
    override var name: String,
    override var maxLength: Int,
    override var type: String,
    override var title: String,
    override var hintMessage: String? = null,
    override var validationPattern: String? = null,
    override var validationMessage: String,
    override var mask: String?
) : InputData, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(maxLength)
        parcel.writeString(type)
        parcel.writeString(title)
        parcel.writeString(hintMessage)
        parcel.writeString(validationPattern)
        parcel.writeString(validationMessage)
        parcel.writeString(mask)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<StepData> {
        override fun createFromParcel(parcel: Parcel): StepData = StepData(parcel)
        override fun newArray(size: Int): Array<StepData?> = arrayOfNulls(size)
    }
}

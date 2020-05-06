package com.mercadolibre.android.cardform.data.model.response

import android.os.Parcel
import android.os.Parcelable

internal data class Validation(val name: String,
                               val values: List<String>,
                               val errorMessage: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(values)
        parcel.writeString(errorMessage)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Validation> {
        override fun createFromParcel(parcel: Parcel) = Validation(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Validation?>(size)
    }
}
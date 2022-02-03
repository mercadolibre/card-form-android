package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcel
import android.os.Parcelable
import com.mercadolibre.android.cardform.base.toBoolean
import com.mercadolibre.android.cardform.base.toInt

internal data class IdentificationData(
    override val name: String,
    override val maxLength: Int = 0,
    override val type: String,
    override val title: String,
    override val hintMessage: String? = null,
    override val validationPattern: String? = null,
    override val validationMessage: String,
    override val mask: String? = null,
    override val autocomplete: Boolean,
    val identifications: List<Identification>
) : InputData, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt().toBoolean(),
        parcel.createTypedArrayList(Identification)!!
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
        parcel.writeInt(autocomplete.toInt())
        parcel.writeTypedList(identifications)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<IdentificationData> {
        override fun createFromParcel(parcel: Parcel) = IdentificationData(parcel)
        override fun newArray(size: Int) = arrayOfNulls<IdentificationData?>(size)
    }
}
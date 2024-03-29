package com.mercadolibre.android.cardform.presentation.model

import android.os.Parcel
import android.os.Parcelable

internal class BinValidator() : Parcelable {

    private var previousBin: String? = null
    private var lastBinValid = ""
    var bin : String? = null
        private set

    constructor(parcel: Parcel) : this() {
        previousBin = parcel.readString()
        bin = parcel.readString()
    }

    fun update(cardNumber: String) {
        previousBin = bin
        bin = if (cardNumber.length < BIN_LENGTH) {
            null
        } else {
            cardNumber.substring(0 until BIN_LENGTH).also {
                lastBinValid = it
            }
        }
    }

    fun getLastValidBin() = lastBinValid

    fun hasChanged() = previousBin != bin

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(previousBin)
        parcel.writeString(bin)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<BinValidator> {
        private const val BIN_LENGTH = 8

        override fun createFromParcel(parcel: Parcel) = BinValidator(parcel)
        override fun newArray(size: Int) = arrayOfNulls<BinValidator?>(size)
    }
}
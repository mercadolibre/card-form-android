package com.mercadolibre.android.cardform.presentation.model

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardNumberStyle
import com.meli.android.carddrawer.model.CardUI
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.data.model.response.CardUi
import com.mercadolibre.android.cardform.presentation.utils.ColorUtils

internal class CardDrawerData(private val cardUi: CardUi) : CardUI, Parcelable {

    private lateinit var namePlaceholder: String
    private lateinit var datePlaceholder: String

    constructor(context: Context, cardUi: CardUi) : this(cardUi) {
        namePlaceholder = context.getString(R.string.cf_card_name_hint)
        datePlaceholder = context.getString(R.string.cf_card_date_hint)
    }

    constructor(parcel: Parcel) : this(parcel.readParcelable<CardUi>(CardUi::class.java.classLoader)!!) {
        namePlaceholder = parcel.readString()!!
        datePlaceholder = parcel.readString()!!
    }

    override fun getCardLogoImageUrl() = cardUi.paymentMethodImageUrl

    override fun getBankImageUrl() = cardUi.issuerImageUrl

    override fun getAnimationType() = CardAnimationType.LEFT_TOP

    override fun getFontType() = cardUi.cardFontType

    override fun getSecurityCodePattern() = cardUi.securityCodeLength

    override fun getExpirationPlaceHolder() = datePlaceholder

    override fun getSecurityCodeLocation() = cardUi.securityCodeLocation

    override fun getCardNumberPattern() = cardUi.cardPattern

    override fun getNamePlaceHolder() = namePlaceholder

    override fun getCardBackgroundColor() = Color.parseColor(cardUi.cardColor)

    override fun getCardFontColor() = Color.parseColor(cardUi.cardFontColor)

    override fun getBankImageRes() = 0
    override fun getCardLogoImageRes() = 0

    override fun getCardNumberStyle(): CardNumberStyle? {
        return cardUi.panStyle?.let {
            CardNumberStyle(
                ColorUtils.safeParseColor(it.textColor, R.color.andes_text_color_white),
                ColorUtils.safeParseColor(it.backgroundColor, R.color.andes_gray_550),
                it.weight
            )
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(cardUi, flags)
        parcel.writeString(namePlaceholder)
        parcel.writeString(datePlaceholder)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CardDrawerData> {
        override fun createFromParcel(parcel: Parcel) = CardDrawerData(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardDrawerData?>(size)
    }
}
package com.mercadolibre.android.cardform.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.presentation.ui.CardFormWebActivity
import com.mercadolibre.android.cardform.presentation.ui.RESPONSE_URL_EXTRA
import com.mercadolibre.android.cardform.presentation.ui.USER_EMAIL_EXTRA
import com.mercadolibre.android.cardform.presentation.ui.USER_NAME_EXTRA

class CardFormWeb: CardForm {

    private constructor(builder: Builder) : super(builder)
    private constructor(parcel: Parcel) : super(parcel)

    fun start(activity: AppCompatActivity,
              requestCode: Int,
              userName: String,
              email: String,
              responseUrl: String) {


        val bundle = getBundle().also {
            it.putString(USER_NAME_EXTRA, userName)
            it.putString(USER_EMAIL_EXTRA, email)
            it.putString(RESPONSE_URL_EXTRA, responseUrl)
        }

        CardFormWebActivity.start(activity,requestCode, bundle)
    }

    class Builder private constructor(siteId: String, flowId: String): CardForm.Builder(siteId, flowId) {

        override fun setExcludedTypes(excludedTypes: List<String>) = apply { super.setExcludedTypes(excludedTypes) }
        override fun setSessionId(sessionId: String) = apply { super.setSessionId(sessionId) }

        override fun build() = CardFormWeb(this)

        companion object {
            @JvmStatic
            fun buildWithPublicKey(publicKey: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setPublicKey(publicKey) as Builder

            @JvmStatic
            fun buildWithAccessToken(accessToken: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setAccessToken(accessToken) as Builder
        }
    }

    companion object CREATOR : Parcelable.Creator<CardFormWeb> {
        override fun createFromParcel(parcel: Parcel) = CardFormWeb(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardFormWeb>(size)
    }
}
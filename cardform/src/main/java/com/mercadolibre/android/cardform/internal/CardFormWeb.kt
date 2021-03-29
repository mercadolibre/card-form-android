package com.mercadolibre.android.cardform.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.presentation.ui.CardFormWebActivity

class CardFormWeb : CardForm {

    private constructor(builder: Builder) : super(builder)
    private constructor(parcel: Parcel) : super(parcel)

    override fun start(activity: AppCompatActivity, requestCode: Int) {
        CardFormWebActivity.start(activity, requestCode, this)
    }

    override fun start(fragment: Fragment, requestCode: Int) {
        CardFormWebActivity.start(fragment, requestCode, this)
    }

    class Builder private constructor(siteId: String, flowId: String) : CardForm.Builder(siteId, flowId) {
        override fun build() = CardFormWeb(this)

        companion object {
            @JvmStatic
            fun withPublicKey(publicKey: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setPublicKey(publicKey) as Builder

            @JvmStatic
            fun withAccessToken(accessToken: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setAccessToken(accessToken) as Builder
        }
    }

    companion object CREATOR : Parcelable.Creator<CardFormWeb> {
        override fun createFromParcel(parcel: Parcel) = CardFormWeb(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardFormWeb>(size)
    }
}

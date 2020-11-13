package com.mercadolibre.android.cardform

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController
import java.io.Serializable
import java.util.UUID

open class CardForm : Parcelable {

    val siteId: String
    val publicKey: String?
    val accessToken: String?
    val excludedTypes: List<String>?
    var requestCode = 0
        protected set
    val sessionId: String
    val flowId: String
    val extraData: Serializable?

    protected constructor(builder: Builder) {
        siteId = builder.siteId
        publicKey = builder.publicKey
        accessToken = builder.accessToken
        excludedTypes = builder.excludedTypes
        flowId = builder.flowId
        sessionId = builder.sessionId ?: UUID.randomUUID().toString()
        extraData = builder.extraData
    }

    protected constructor(parcel: Parcel) {
        siteId = parcel.readString()!!
        publicKey = parcel.readString()
        accessToken = parcel.readString()
        excludedTypes = parcel.createStringArrayList()
        flowId = parcel.readString()!!
        sessionId = parcel.readString()!!
        extraData = parcel.readSerializable()
    }

    fun start(activity: AppCompatActivity, requestCode: Int) {
        this.requestCode = requestCode
        FragmentNavigationController.reset()
        CardFormActivity.start(activity, requestCode, this)
        activity.overridePendingTransition(
            R.anim.slide_right_to_left_in,
            R.anim.slide_right_to_left_out
        )
    }

    open class Builder protected constructor(val siteId: String, val flowId: String) {
        var excludedTypes: List<String>? = null
            private set

        var publicKey: String? = null
            private set

        var accessToken: String? = null
            private set

        var sessionId: String? = null
            private set

        var extraData: Serializable? = null

        fun setExtraData(extraData: Serializable?) = apply {
            this.extraData = extraData
        }

        open fun setExcludedTypes(excludedTypes: List<String>) = apply {
            this.excludedTypes = excludedTypes
        }

        open fun setSessionId(sessionId: String) = apply { this.sessionId = sessionId }

        protected fun setPublicKey(publicKey: String) = apply { this.publicKey = publicKey }

        protected fun setAccessToken(accessToken: String) = apply { this.accessToken = accessToken }

        open fun build() = CardForm(this)

        companion object {
            @JvmStatic
            fun withPublicKey(publicKey: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setPublicKey(publicKey)

            @JvmStatic
            fun withAccessToken(accessToken: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setAccessToken(accessToken)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(siteId)
        parcel.writeString(publicKey)
        parcel.writeString(accessToken)
        parcel.writeStringList(excludedTypes)
        parcel.writeString(flowId)
        parcel.writeString(sessionId)
        parcel.writeSerializable(extraData)
    }

    override fun describeContents() = 0

    companion object {
        const val RESULT_CARD_ID_KEY = "associated_card_id"

        @JvmField
        val CREATOR: Parcelable.Creator<CardForm> = object : Parcelable.Creator<CardForm> {
            override fun createFromParcel(parcel: Parcel) = CardForm(parcel)
            override fun newArray(size: Int) = arrayOfNulls<CardForm?>(size)
        }
    }
}
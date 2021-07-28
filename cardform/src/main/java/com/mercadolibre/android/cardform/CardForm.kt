package com.mercadolibre.android.cardform

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController
import com.mercadolibre.android.cardform.service.CardFormIntent
import com.mercadolibre.android.cardform.service.CardFormService
import java.util.*

internal const val CARD_FORM_EXTRA = "card_form"
internal const val EXIT_ANIM_EXTRA = "exit_anim"

open class CardForm : Parcelable {

    val siteId: String
    val publicKey: String?
    val accessToken: String?
    val excludedTypes: List<String>?
    var requestCode = 0
        protected set
    val sessionId: String
    val flowId: String
    val cardFormIntent: Intent?
    val cardInfo: CardInfoDto?
    val acceptThirdPartyCard: Boolean
    val activateCard: Boolean

    protected constructor(builder: Builder) {
        siteId = builder.siteId
        publicKey = builder.publicKey
        accessToken = builder.accessToken
        excludedTypes = builder.excludedTypes
        flowId = builder.flowId
        sessionId = builder.sessionId ?: UUID.randomUUID().toString()
        cardFormIntent = builder.cardFormIntent
        cardInfo = builder.cardInfo
        acceptThirdPartyCard = builder.acceptThirdPartyCard
        activateCard = builder.activateCard
    }

    protected constructor(parcel: Parcel) {
        siteId = parcel.readString()!!
        publicKey = parcel.readString()
        accessToken = parcel.readString()
        excludedTypes = parcel.createStringArrayList()
        flowId = parcel.readString()!!
        sessionId = parcel.readString()!!
        cardFormIntent = parcel.readParcelable(CardFormIntent::class.java.classLoader)
        cardInfo = parcel.readParcelable(CardInfoDto::class.java.classLoader)
        acceptThirdPartyCard = parcel.readByte() != 0.toByte()
        activateCard = parcel.readByte() != 0.toByte()
    }

    open fun start(activity: AppCompatActivity, requestCode: Int) {
        start(activity, requestCode, R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out)
    }

    fun start(activity: AppCompatActivity, requestCode: Int, enterAnim: Int, exitAnim: Int) {
        this.requestCode = requestCode
        FragmentNavigationController.reset()
        CardFormActivity.start(activity, requestCode, this, exitAnim)
        activity.overridePendingTransition(
            enterAnim,
            exitAnim
        )
    }

    open fun start(fragment: Fragment, requestCode: Int) {
        start(fragment, requestCode, R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out)
    }

    fun start(fragment: Fragment, requestCode: Int, enterAnim: Int, exitAnim: Int) {
        this.requestCode = requestCode
        FragmentNavigationController.reset()
        CardFormActivity.start(fragment, requestCode, this, exitAnim)
        fragment.activity?.overridePendingTransition(
            enterAnim,
            exitAnim
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

        var cardFormIntent: Intent? = null
            private set

        var cardInfo: CardInfoDto? = null
            private set

        var acceptThirdPartyCard: Boolean = true
            private set

        var activateCard: Boolean = true
            private set

        open fun setExcludedTypes(excludedTypes: List<String>) = apply {
            this.excludedTypes = excludedTypes
        }

        open fun setSessionId(sessionId: String) = apply { this.sessionId = sessionId }

        open fun setThirdPartyCard(acceptThirdPartyCard: Boolean, activateCard: Boolean) = apply {
            this.acceptThirdPartyCard = acceptThirdPartyCard
            this.activateCard = activateCard
        }

        fun <T : CardFormService> setCardFormHandler(handlerIntent: CardFormIntent<T>) = apply { cardFormIntent = handlerIntent }

        protected fun setPublicKey(publicKey: String) = apply { this.publicKey = publicKey }

        protected fun setAccessToken(accessToken: String) = apply { this.accessToken = accessToken }

        fun setCardInfo(cardInfo: CardInfoDto) = apply { this.cardInfo = cardInfo }

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
        parcel.writeParcelable(cardFormIntent, flags)
        parcel.writeParcelable(cardInfo, flags)
        parcel.writeByte(if(acceptThirdPartyCard) 1 else 0)
        parcel.writeByte(if(activateCard) 1 else 0)
    }

    override fun describeContents() = 0

    companion object {
        const val RESULT_CARD_ID_KEY = "associated_card_id"
        const val RESULT_BIN_KEY = "associated_bin"
        const val RESULT_PAYMENT_TYPE_KEY = "associated_payment_type"
        const val RESULT_LAST_FOUR_DIGITS_KEY = "result_last_four_digits_key"

        @JvmField
        val CREATOR: Parcelable.Creator<CardForm> = object : Parcelable.Creator<CardForm> {
            override fun createFromParcel(parcel: Parcel) = CardForm(parcel)
            override fun newArray(size: Int) = arrayOfNulls<CardForm?>(size)
        }
    }
}
package com.mercadolibre.android.cardform

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import com.mercadolibre.android.cardform.presentation.ui.CardFormFragment
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController
import java.util.*

class CardForm : Parcelable {

    val siteId: String
    val publicKey: String?
    val accessToken: String?
    val excludedTypes: List<String>?
    var requestCode = 0
        private set
    val sessionId: String
    val flowId: String

    private constructor(builder: Builder) {
        siteId = builder.siteId
        publicKey = builder.publicKey
        accessToken = builder.accessToken
        excludedTypes = builder.excludedTypes
        flowId = builder.flowId
        sessionId = builder.sessionId ?: UUID.randomUUID().toString()
    }

    private constructor(parcel: Parcel) {
        siteId = parcel.readString()!!
        publicKey = parcel.readString()
        accessToken = parcel.readString()
        excludedTypes = parcel.createStringArrayList()
        flowId = parcel.readString()!!
        sessionId = parcel.readString()!!
    }

    fun start(activity: AppCompatActivity, requestCode: Int) {
        this.requestCode = requestCode

        CardFormActivity.start(activity, requestCode, this)
        activity.overridePendingTransition(
            R.anim.slide_right_to_left_in,
            R.anim.slide_right_to_left_out
        )
    }

    fun start(fragmentManager: FragmentManager, requestCode: Int, containerId: Int) {
        this.requestCode = requestCode

        fragmentManager.beginTransaction().apply {

            FragmentNavigationController.reset()

            setCustomAnimations(0, R.anim.cf_fake_in, 0, R.anim.cf_fake_out)

            // Added from card form fragment
            replace(
                containerId,
                CardFormFragment.newInstance(true, this@CardForm),
                CardFormFragment.TAG
            )

            addToBackStack(CardFormFragment.TAG)
            commitAllowingStateLoss()
        }
    }

    class Builder private constructor(val siteId: String) {
        var excludedTypes: List<String>? = null
            private set

        var publicKey: String? = null
            private set

        var accessToken: String? = null
            private set

        var sessionId: String? = null
            private set

        lateinit var flowId: String
            private set

        fun setExcludedTypes(excludedTypes: List<String>) = apply {
            this.excludedTypes = excludedTypes
        }

        private fun setPublicKey(publicKey: String) = apply { this.publicKey = publicKey }

        private fun setAccessToken(accessToken: String) = apply { this.accessToken = accessToken }

        private fun setFlowId(flowId: String) = apply { this.flowId = flowId }

        fun setSessionId(sessionId: String) = apply { this.sessionId = sessionId }

        fun build() = CardForm(this)

        companion object {
            @JvmStatic
            fun withPublicKey(publicKey: String, siteId: String, flowId: String) =
                Builder(siteId).setPublicKey(publicKey).setFlowId(flowId)

            @JvmStatic
            fun withAccessToken(accessToken: String, siteId: String, flowId: String) =
                Builder(siteId).setAccessToken(accessToken).setFlowId(flowId)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(siteId)
        parcel.writeString(publicKey)
        parcel.writeString(accessToken)
        parcel.writeStringList(excludedTypes)
        parcel.writeString(flowId)
        parcel.writeString(sessionId)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CardForm> {
        override fun createFromParcel(parcel: Parcel) = CardForm(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardForm?>(size)
    }
}
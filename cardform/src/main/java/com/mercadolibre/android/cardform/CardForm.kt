package com.mercadolibre.android.cardform

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import com.mercadolibre.android.cardform.presentation.ui.CardFormFragment
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController

class CardForm : Parcelable {

    val siteId: String
    val publicKey: String?
    val accessToken: String?
    val excludedTypes: List<String>?
    var requestCode = 0
        private set

    private constructor(builder: Builder) {
        siteId = builder.siteId
        publicKey = builder.publicKey
        accessToken = builder.accessToken
        excludedTypes = builder.excludedTypes
    }

    private constructor(parcel: Parcel) {
        siteId = parcel.readString()!!
        publicKey = parcel.readString()
        accessToken = parcel.readString()
        excludedTypes = parcel.createStringArrayList()
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

            setCustomAnimations(
                0,
                R.anim.fade_out, 0,
                R.anim.fade_out
            )

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

        fun setExcludedTypes(excludedTypes: List<String>) = apply {
            this.excludedTypes = excludedTypes
        }

        private fun setPublicKey(publicKey: String) = apply { this.publicKey = publicKey }

        private fun setAccessToken(accessToken: String) = apply { this.accessToken = accessToken }

        fun build() = CardForm(this)

        companion object {
            @JvmStatic fun withPublicKey(publicKey: String, siteId: String) =
                Builder(siteId).setPublicKey(publicKey)
            @JvmStatic fun withAccessToken(accessToken: String, siteId: String) =
                Builder(siteId).setAccessToken(accessToken)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(siteId)
        parcel.writeString(publicKey)
        parcel.writeString(accessToken)
        parcel.writeStringList(excludedTypes)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<CardForm> {
        override fun createFromParcel(parcel: Parcel) = CardForm(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardForm?>(size)
    }
}
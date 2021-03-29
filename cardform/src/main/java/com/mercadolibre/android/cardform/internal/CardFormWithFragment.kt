package com.mercadolibre.android.cardform.internal

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.FragmentManager
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.CardInfoDto
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.ui.CardFormFragment
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController

class CardFormWithFragment : CardForm {

    private constructor(builder: Builder) : super(builder)
    private constructor(parcel: Parcel) : super(parcel)

    fun start(fragmentManager: FragmentManager, requestCode: Int, containerId: Int) {
        this.requestCode = requestCode

        fragmentManager.beginTransaction().apply {

            FragmentNavigationController.reset()

            setCustomAnimations(0, R.anim.cf_fake_in, 0, R.anim.cf_fake_out)

            // Added from card form fragment
            replace(
                containerId,
                CardFormFragment.newInstance(true, this@CardFormWithFragment,
                    R.anim.slide_right_to_left_out),
                TAG
            )

            addToBackStack(TAG)
            commitAllowingStateLoss()
        }
    }

    class Builder private constructor(siteId: String, flowId: String) : CardForm.Builder(siteId, flowId) {
        override fun setExcludedTypes(excludedTypes: List<String>) =
            apply { super.setExcludedTypes(excludedTypes) }

        override fun setSessionId(sessionId: String) = apply { super.setSessionId(sessionId) }

        fun setCardInfoNew(cardInfo: CardInfoDto) = apply { super.setCardInfo(cardInfo) }

        override fun build() = CardFormWithFragment(this)

        companion object {
            @JvmStatic
            fun withPublicKey(publicKey: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setPublicKey(publicKey) as Builder

            @JvmStatic
            fun withAccessToken(accessToken: String, siteId: String, flowId: String) =
                Builder(siteId, flowId).setAccessToken(accessToken) as Builder
        }
    }

    companion object CREATOR : Parcelable.Creator<CardFormWithFragment> {
        const val TAG = "card_form"
        override fun createFromParcel(parcel: Parcel) = CardFormWithFragment(parcel)
        override fun newArray(size: Int) = arrayOfNulls<CardFormWithFragment?>(size)
    }
}
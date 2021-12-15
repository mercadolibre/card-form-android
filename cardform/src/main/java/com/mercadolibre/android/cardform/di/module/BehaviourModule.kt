package com.mercadolibre.android.cardform.di.module

import com.mercadopago.android.px.addons.BehaviourProvider
import com.mercadopago.android.px.addons.TokenDeviceBehaviour

internal class BehaviourModule(sessionId: String) {
    val escManager by lazy {
        BehaviourProvider.getEscManagerBehaviour(sessionId, true)
    }

    val trackerBehaviour by lazy { BehaviourProvider.getTrackingBehaviour(CARD_FORM_CONTEXT) }

    val tokenDeviceBehaviour: TokenDeviceBehaviour by lazy { BehaviourProvider.getTokenDeviceBehaviour() }

    companion object {
        private const val CARD_FORM_CONTEXT = "card_form"
    }
}

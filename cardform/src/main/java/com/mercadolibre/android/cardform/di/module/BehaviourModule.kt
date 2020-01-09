package com.mercadolibre.android.cardform.di.module

import com.mercadopago.android.px.addons.BehaviourProvider

class BehaviourModule(sessionId: String) {
    val escManager by lazy {
        BehaviourProvider.getEscManagerBehaviour(sessionId, true)
    }
}
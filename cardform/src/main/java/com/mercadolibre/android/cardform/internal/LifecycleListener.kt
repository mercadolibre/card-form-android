package com.mercadolibre.android.cardform.internal

interface LifecycleListener {
    @JvmDefault fun onCardAdded(cardId: String, pan: String, exp: String, cvv: String, callback: Callback) = callback.onSuccess()

    interface Callback {
        fun onSuccess()
        fun onError()
    }
}
package com.mercadolibre.android.cardform

interface LifecycleListener {
    @JvmDefault fun onCardAdded(cardId: String, callback: Callback) = callback.onSuccess()

    interface Callback {
        fun onSuccess()
        fun onError()
    }
}
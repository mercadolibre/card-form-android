package com.mercadolibre.android.example

import android.util.Log
import com.mercadolibre.android.cardform.service.CardFormRequestHandler

class SampleRequestHandler: CardFormRequestHandler() {
    override fun onCardAdded(cardId: String, callback: CallBack) {
        Log.i("CARD_FORM", "Process card with id: $cardId...")
        postDelayed({
            Log.i("CARD_FORM", "Process card complete")
            callback.call()
        }, 5000)
    }
}
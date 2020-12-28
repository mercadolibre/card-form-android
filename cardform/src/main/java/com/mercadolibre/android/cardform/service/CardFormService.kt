package com.mercadolibre.android.cardform.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Messenger

abstract class CardFormService(
    private val cardFormRequestHandler: CardFormRequestHandler
) : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(cardFormRequestHandler).binder
    }
}
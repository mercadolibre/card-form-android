package com.mercadolibre.android.cardform.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Messenger

internal const val CARD_FORM_HANDLE_EXTRA = "card_form_handle"

/**
 * Register the client and send the cardId value for the integrator to process.
 */
internal const val MSG_REGISTER_CLIENT = 1

/**
 * Association process is finished
 */
internal const val MSG_PROCESS_FINISHED = 2

internal class CardFormService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return intent?.let {
            it.getParcelableExtra<CardFormHandler>(CARD_FORM_HANDLE_EXTRA)
                ?.let { handler -> Messenger(handler).binder }
        }
    }
}
package com.mercadolibre.android.cardform.service

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Register the client and send the cardId value for the integrator to process.
 */
internal const val MSG_REGISTER_CLIENT = 1

/**
 * Association process is finished
 */
internal const val MSG_PROCESS_FINISHED = 2


internal class CardFormServiceManager(private val context: Context) {

    private lateinit var cardFormServiceConnection: CardFormServiceConnection

    fun onBindService(intent: Intent, block: () -> Unit) {
        cardFormServiceConnection = CardFormServiceConnection(IncomingHandler {
            context.unbindService(cardFormServiceConnection)
            block()
        })
        context.bindService(
            intent,
            cardFormServiceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun setDataBundle(data: Bundle) {
        cardFormServiceConnection.setDataBundle(data)
    }
}
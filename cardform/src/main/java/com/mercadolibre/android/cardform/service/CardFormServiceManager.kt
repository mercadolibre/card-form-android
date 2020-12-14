package com.mercadolibre.android.cardform.service

import android.content.Context
import android.content.Intent
import android.os.Bundle

internal class CardFormServiceManager(
    private val cardFormHandler: CardFormHandler,
    incomingHandler: IncomingHandler
) {

    private val cardFormServiceConnection = CardFormServiceConnection(incomingHandler)

    fun onBindService(context: Context) {
        val intent = Intent(context, CardFormService::class.java)
        intent.putExtra(CARD_FORM_HANDLE_EXTRA, cardFormHandler)
        context.bindService(
            intent,
            cardFormServiceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun onUnbindService(context: Context) {
        context.unbindService(cardFormServiceConnection)
    }

    fun setDataBundle(data: Bundle) {
        cardFormServiceConnection.setDataBundle(data)
    }
}
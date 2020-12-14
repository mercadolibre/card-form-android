package com.mercadolibre.android.cardform.service

import android.os.*

abstract class CardFormHandler : Handler(), Parcelable {
    private lateinit var client: Messenger

    abstract fun onProcessResult(resultData: Bundle)

    fun onProcessComplete() {
        client.send(Message.obtain(null, MSG_PROCESS_FINISHED))
    }

    override fun handleMessage(msg: Message) {
        if (msg.what == MSG_REGISTER_CLIENT) {
            client = msg.replyTo
            onProcessResult(msg.data)
        } else {
            super.handleMessage(msg)
        }
    }
}
package com.mercadolibre.android.cardform.service

import android.os.Handler
import android.os.Message
import android.os.Messenger
import com.mercadolibre.android.cardform.CardForm.Companion.RESULT_CARD_ID_KEY

abstract class CardFormRequestHandler: Handler() {

    private lateinit var client: Messenger

    abstract fun onCardAdded(cardId: String, callback: CallBack)

    override fun handleMessage(msg: Message) {
        if (msg.what == MSG_REGISTER_CLIENT) {
            client = msg.replyTo
            msg.data.getString(RESULT_CARD_ID_KEY)?.also { onCardAdded(it, object : CallBack {
                override fun call() { client.send(Message.obtain(null, MSG_PROCESS_FINISHED)) }
            }) }
        } else {
            super.handleMessage(msg)
        }
    }

    interface CallBack {
        fun call()
    }
}
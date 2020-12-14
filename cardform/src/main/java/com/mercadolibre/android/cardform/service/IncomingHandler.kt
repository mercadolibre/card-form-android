package com.mercadolibre.android.cardform.service

import android.os.Handler
import android.os.Message

internal class IncomingHandler(private val block: () -> Unit) : Handler() {

    override fun handleMessage(msg: Message) {
        if (msg.what == MSG_PROCESS_FINISHED) {
            block()
        } else {
            super.handleMessage(msg)
        }
    }
}
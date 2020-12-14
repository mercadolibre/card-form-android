package com.mercadolibre.android.cardform.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger

internal class CardFormServiceConnection(incomingHandler: IncomingHandler) : ServiceConnection {

    private var messengerService: Messenger? = null
    private val messenger = Messenger(incomingHandler)
    private var dataBundle = Bundle()

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        messengerService = Messenger(service)
        val msg = Message.obtain(null, MSG_REGISTER_CLIENT).also {
            it.data = dataBundle
            it.replyTo = messenger
        }
        messengerService?.send(msg)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        messengerService = null
    }

    fun setDataBundle(data: Bundle) {
        dataBundle = data
    }
}
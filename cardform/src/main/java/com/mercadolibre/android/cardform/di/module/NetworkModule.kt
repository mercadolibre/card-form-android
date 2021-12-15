package com.mercadolibre.android.cardform.di.module

import android.content.Context
import com.mercadolibre.android.cardform.network.GsonFactory
import com.mercadolibre.android.cardform.network.RetrofitFactory

internal class NetworkModule(context: Context, sessionId: String, flowId: String, accessToken: String?) {
    val gson by lazy { GsonFactory.get() }
    val retrofit by lazy { RetrofitFactory.get(context.applicationContext, gson, sessionId, flowId, accessToken) }
}

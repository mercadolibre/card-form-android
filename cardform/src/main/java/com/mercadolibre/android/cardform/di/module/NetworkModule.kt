package com.mercadolibre.android.cardform.di.module

import android.content.Context
import com.mercadolibre.android.cardform.network.RetrofitFactory

internal class NetworkModule(context: Context, sessionId: String) {

    val retrofit by lazy { RetrofitFactory.get(context.applicationContext, sessionId) }
}
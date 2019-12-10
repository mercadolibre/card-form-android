package com.mercadolibre.android.cardform.di.module

import android.content.Context
import com.mercadolibre.android.cardform.network.RetrofitFactory

class NetworkModule(private val context: Context) {

    val retrofit by lazy { RetrofitFactory.get(context.applicationContext) }
}
package com.mercadolibre.android.cardform.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitFactory {

    const val MP_API_BASE_URL = "https://api.mercadopago.com"
    const val MP_API_INTERNAL_BASE_URL = "https://api.mercadopago.com"
    private const val DEFAULT_READ_TIMEOUT = 20
    private const val DEFAULT_CONNECT_TIMEOUT = 10
    private const val DEFAULT_WRITE_TIMEOUT = 20

    fun get(context: Context, baseUrl: String, sessionId: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.get()))
            .client(HttpClientFactory.get(context, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT,
                DEFAULT_WRITE_TIMEOUT, sessionId))
            .build()
    }
}
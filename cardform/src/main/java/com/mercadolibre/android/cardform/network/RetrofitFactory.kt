package com.mercadolibre.android.cardform.network

import android.content.Context
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitFactory {

    private const val MP_API_BASE_URL = "https://api.mercadopago.com"
    private const val DEFAULT_READ_TIMEOUT = 20
    private const val DEFAULT_CONNECT_TIMEOUT = 10
    private const val DEFAULT_WRITE_TIMEOUT = 20

    fun get(context: Context, gson: Gson, sessionId: String, flowId: String, accessToken: String?) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(MP_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(HttpClientFactory.get(context, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT,
                DEFAULT_WRITE_TIMEOUT, sessionId, flowId, accessToken))
            .build()
    }
}

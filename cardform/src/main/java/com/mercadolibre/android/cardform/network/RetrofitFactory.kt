package com.mercadolibre.android.cardform.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private const val MP_API_BASE_URL = "https://api.mercadopago.com"
    private const val DEFAULT_READ_TIMEOUT = 20
    private const val DEFAULT_CONNECT_TIMEOUT = 10
    private const val DEFAULT_WRITE_TIMEOUT = 20

    fun get(context: Context) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(MP_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.get()))
            .client(HttpClientFactory.get(context, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT,
                DEFAULT_WRITE_TIMEOUT))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}
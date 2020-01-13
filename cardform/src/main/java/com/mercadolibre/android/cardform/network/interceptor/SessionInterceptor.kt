package com.mercadolibre.android.cardform.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(private val sessionId: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().run {
            newBuilder()
                .addHeader(SESSION_ID, sessionId)
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val SESSION_ID = "session-id"
    }
}
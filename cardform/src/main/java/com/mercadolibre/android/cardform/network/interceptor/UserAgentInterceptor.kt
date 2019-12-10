package com.mercadolibre.android.cardform.network.interceptor

import com.mercadolibre.android.cardform.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor : Interceptor {

    companion object {
        private const val HEADER = "user-agent"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header(HEADER, BuildConfig.USER_AGENT)
            .build()
        return chain.proceed(request)
    }
}
package com.mercadolibre.android.cardform.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class FlowIdInterceptor(private val flowId: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().run {
            newBuilder()
                .addHeader(FLOW_ID, flowId)
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val FLOW_ID = "X-Flow-Id"
    }
}
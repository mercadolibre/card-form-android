package com.mercadolibre.android.cardform.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class ProductIdInterceptor : Interceptor {

    companion object {
        private const val HEADER = "x-product-id"
        private const val VALUE = "BJEO9NVBF6RG01IIIOTG"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header(HEADER, VALUE)
            .build()
        return chain.proceed(request)
    }
}
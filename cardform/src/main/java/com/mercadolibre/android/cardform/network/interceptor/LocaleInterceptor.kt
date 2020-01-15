package com.mercadolibre.android.cardform.network.interceptor

import android.content.Context
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response

internal class LocaleInterceptor(private val context: Context) : Interceptor {

    companion object {
        private const val HEADER = "accept-language"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header(HEADER, getLocale())
            .build()
        return chain.proceed(request)
    }

    @Suppress("DEPRECATION")
    private fun getLocale(): String {
        val configuration = context.resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val locales = configuration.locales
            if (!locales.isEmpty) {
                return locales.get(0).language
            }
        }
        return configuration.locale.language
    }
}
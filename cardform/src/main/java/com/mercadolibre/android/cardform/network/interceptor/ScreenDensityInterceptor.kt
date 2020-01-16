package com.mercadolibre.android.cardform.network.interceptor

import android.content.Context
import android.util.DisplayMetrics
import okhttp3.Interceptor
import okhttp3.Response

internal class ScreenDensityInterceptor(private val context: Context) : Interceptor {

    companion object {
        private const val HEADER = "x-density"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header(HEADER, getDensityName())
            .build()
        return chain.proceed(request)
    }

    private fun getDensityName(): String {
        val densityScale = 1.0f / DisplayMetrics.DENSITY_DEFAULT
        val density = context.resources.displayMetrics.density / densityScale

        return when {
            density >= DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            density >= DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            density >= DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            density >= DisplayMetrics.DENSITY_HIGH -> "hdpi"
            density >= DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            else -> "ldpi"
        }
    }
}
package com.mercadolibre.android.cardform.presentation.utils

import com.mercadolibre.android.cardform.di.Dependencies

internal object JsonUtils {

    private val gson = Dependencies.instance.networkModule?.gson ?: error("Dependencies not initialized")

    fun <T> fromJson(json: String, javaClass: Class<T>): T {
        return gson.fromJson(json, javaClass)
    }
}

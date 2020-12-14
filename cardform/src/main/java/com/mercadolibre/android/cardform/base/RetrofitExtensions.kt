package com.mercadolibre.android.cardform.base

import org.json.JSONObject
import retrofit2.Response

internal fun <T> Response<T>.resolveRetrofitResponse(): T {
    return when {
        !isSuccessful -> {
            //https://github.com/square/retrofit/issues/3255
            val errorBody = errorBody()?.string()
            val errorMessage = if (errorBody.isNullOrEmpty()) {
                "unknown error"
            } else {
                val jsonError = JSONObject(errorBody)
                jsonError.getString("message")
            }
            throw Exception(errorMessage)
        }
        else -> body() ?: throw Exception("Response data should not be null")
    }
}
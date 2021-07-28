package com.mercadolibre.android.cardform.base

import com.mercadolibre.android.cardform.network.exceptions.ThirdPartyException
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

            val userErrorMessage = if (errorBody.isNullOrEmpty()) {
                "unknown error"
            } else {
                val jsonError = JSONObject(errorBody)
                jsonError.getString("user_error_message")
            }

            if(!userErrorMessage.isNullOrBlank()) {
                throw ThirdPartyException(JSONObject(errorBody))
            }

            throw Exception(errorMessage)
        }
        else -> body() ?: throw Exception("Response data should not be null")
    }
}
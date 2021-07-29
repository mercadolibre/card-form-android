package com.mercadolibre.android.cardform.base

import com.mercadolibre.android.cardform.network.exceptions.BusinessException
import org.json.JSONObject
import retrofit2.Response

internal fun <T> Response<T>.resolveRetrofitResponse(): T {
    return when {
        !isSuccessful -> {
            //https://github.com/square/retrofit/issues/3255
            val errorBody = errorBody()?.string()
            val exception = if (errorBody.isNullOrEmpty()) {
                Exception("unknown error")
            } else {
                val jsonError = JSONObject(errorBody)

                if(jsonError.has("user_error_message")) {
                    BusinessException(jsonError)
                } else {
                    Exception(jsonError.getString("message"))
                }
            }

            throw exception
        }
        else -> body() ?: throw Exception("Response data should not be null")
    }
}
package com.mercadolibre.android.cardform.network.exceptions

import org.json.JSONObject

internal class BusinessException internal constructor(
    error: String,
    status: Int,
    userErrorMessage: String
) : CardFormException(error, status, userErrorMessage) {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("error"),
        jsonObject.getInt("status"),
        jsonObject.getString("user_error_message")
    )
}
package com.mercadolibre.android.cardform.network.exceptions

import org.json.JSONObject
import java.lang.Exception

internal open class ThirdPartyException(
    private var error: String,
    private var status: Int,
    private var userErrorMessage: String,
    override var message: String
) : Exception() {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("error"),
        jsonObject.getInt("status"),
        jsonObject.getString("message"),
        jsonObject.getString("user_error_message")
    )
}
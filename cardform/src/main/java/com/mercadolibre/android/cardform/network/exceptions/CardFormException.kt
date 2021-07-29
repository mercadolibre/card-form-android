package com.mercadolibre.android.cardform.network.exceptions

import org.json.JSONObject
import java.lang.Exception

internal open class CardFormException internal constructor(
    private var error: String,
    private var status: Int,
    override var message: String
) : Exception() {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("error"),
        jsonObject.getInt("status"),
        jsonObject.getString("message")
    )
}
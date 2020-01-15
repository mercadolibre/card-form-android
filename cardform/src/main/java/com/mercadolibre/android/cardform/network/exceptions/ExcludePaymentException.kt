package com.mercadolibre.android.cardform.network.exceptions

import org.json.JSONObject

internal class ExcludePaymentException(jsonObject: JSONObject) : CardFormException(jsonObject)
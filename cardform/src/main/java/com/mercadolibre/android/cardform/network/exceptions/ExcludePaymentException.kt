package com.mercadolibre.android.cardform.network.exceptions

import org.json.JSONObject

class ExcludePaymentException(jsonObject: JSONObject) : CardFormException(jsonObject)
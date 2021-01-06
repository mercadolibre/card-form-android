package com.mercadolibre.android.cardform.service

import android.content.Context
import android.content.Intent

class CardFormIntent<out T : CardFormService>(
    context: Context,
    serviceClass: Class<T>
) : Intent(context, serviceClass) {

    init {
        setPackage(context.packageName)
    }
}
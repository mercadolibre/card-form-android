package com.mercadolibre.android.cardform.di.module

import android.content.Context
import android.content.Intent
import com.mercadolibre.android.cardform.service.CardFormServiceManager

internal class ServiceModule(
    context: Context,
    intent: Intent
) {
    val cardFormServiceManager by lazy {
        CardFormServiceManager(
            context.applicationContext,
            intent
        )
    }
}
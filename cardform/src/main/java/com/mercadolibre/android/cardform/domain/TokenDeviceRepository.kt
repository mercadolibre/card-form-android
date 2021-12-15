package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadopago.android.px.addons.tokenization.Tokenize

internal interface TokenDeviceRepository {
    val isFeatureAvailable: Boolean
    fun get(cardId: String): ResponseCallback<Tokenize>
}

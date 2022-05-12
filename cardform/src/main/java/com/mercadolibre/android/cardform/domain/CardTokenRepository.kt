package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardTokenDM

internal interface CardTokenRepository {
    suspend fun get(cardInfoBody: CardInfoBody): ResponseCallback<CardTokenDM>
}

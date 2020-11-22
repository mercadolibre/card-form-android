package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import com.mercadolibre.android.cardform.domain.TokenizeWebCardParam

internal interface TokenizeRepository {
    suspend fun tokenizeCard(cardInfoBody: CardInfoBody) : ResponseCallback<CardToken>
    suspend fun tokenizeWebCard(param: TokenizeWebCardParam) : ResponseCallback<CardToken>
}
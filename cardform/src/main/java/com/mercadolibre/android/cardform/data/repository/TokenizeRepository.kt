package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken

interface TokenizeRepository {
    suspend fun tokenizeCard(cardInfoBody: CardInfoBody) : CardToken?
}
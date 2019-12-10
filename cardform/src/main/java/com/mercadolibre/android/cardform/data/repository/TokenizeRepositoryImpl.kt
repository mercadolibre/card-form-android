package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import com.mercadolibre.android.cardform.data.service.TokenizeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenizeRepositoryImpl(private val service: TokenizeService,
                             private val accessToken: String) : TokenizeRepository {
    override suspend fun tokenizeCard(cardInfoBody: CardInfoBody): CardToken? {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                val response = service.createTokenAsync(accessToken, cardInfoBody).await()
                if (response.isSuccessful) response.body()
                else null
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
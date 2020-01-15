package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import com.mercadolibre.android.cardform.data.service.TokenizeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

internal class TokenizeRepositoryImpl(
    private val service: TokenizeService,
    private val accessToken: String
) : TokenizeRepository {
    override suspend fun tokenizeCard(cardInfoBody: CardInfoBody): CardToken? {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                with(service.createTokenAsync(accessToken, cardInfoBody).await()) {
                    if (isSuccessful) {
                        body()
                    } else {
                        //https://github.com/square/retrofit/issues/3255
                        val jsonError = JSONObject(errorBody()?.string())
                        throw IOException(jsonError.getString("message"))
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
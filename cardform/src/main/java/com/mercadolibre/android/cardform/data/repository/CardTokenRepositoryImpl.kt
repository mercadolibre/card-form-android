package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.service.TokenizeService
import com.mercadolibre.android.cardform.domain.CardTokenRepository
import kotlinx.coroutines.withContext

internal class CardTokenRepositoryImpl(
    private val tokenizeService: TokenizeService,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : CardTokenRepository {

    override suspend fun get(cardInfoBody: CardInfoBody) =
        withContext(contextProvider.IO) {
            runCatching {
                tokenizeService
                    .createTokenAsync(cardInfoBody)
                    .resolveRetrofitResponse()
            }.fold(::Success, ::Failure)
        }
}

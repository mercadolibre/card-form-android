package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.mapper.WebCardTokenBodyMapper
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.service.TokenizeService
import com.mercadolibre.android.cardform.domain.TokenizeWebCardParam
import kotlinx.coroutines.withContext

internal class TokenizeRepositoryImpl(
    private val accessToken: String,
    private val tokenizeService: TokenizeService,
    private val webCardTokenBodyMapper: WebCardTokenBodyMapper,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : TokenizeRepository {

    override suspend fun tokenizeCard(cardInfoBody: CardInfoBody) =
        withContext(contextProvider.IO) {
            runCatching {
                tokenizeService
                    .createTokenAsync(accessToken, cardInfoBody)
                    .resolveRetrofitResponse()
            }.fold(::Success, ::Failure)
        }

    override suspend fun tokenizeWebCard(param: TokenizeWebCardParam) =
        withContext(contextProvider.IO) {
            runCatching {
                tokenizeService
                    .createWebCardToken(accessToken, webCardTokenBodyMapper.map(param))
                    .resolveRetrofitResponse()
            }.fold(::Success, ::Failure)
        }
}
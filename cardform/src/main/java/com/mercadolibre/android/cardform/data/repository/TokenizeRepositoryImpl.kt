package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.mapper.WebCardTokenBodyMapper
import com.mercadolibre.android.cardform.data.model.body.CardHolder
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.service.TokenizeService
import com.mercadolibre.android.cardform.domain.TokenizeWebCardParam
import kotlinx.coroutines.withContext

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class TokenizeRepositoryImpl(
    private val tokenizeService: TokenizeService,
    private val accessToken: String,
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

internal data class WebCardTokenBody(
    val cardNumberId: String,
    val truncCardNumber: String,
    val siteId: String,
    val cardholder: CardHolder,
    val expirationMonth: Int,
    val expirationYear: Int,
    val cardNumberLength: Int,
    val publicKey: String? = null
)
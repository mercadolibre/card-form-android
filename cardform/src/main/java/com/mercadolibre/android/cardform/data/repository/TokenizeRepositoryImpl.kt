package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.service.TokenizeService
import kotlinx.coroutines.withContext
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class TokenizeRepositoryImpl(
    private val service: TokenizeService,
    private val accessToken: String,
    private val siteId: String,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : TokenizeRepository {

    override suspend fun tokenizeCard(cardInfoBody: CardInfoBody) = runCatching {
        withContext(contextProvider.IO) {
            service.createTokenAsync(accessToken, cardInfoBody)
        }
    }.mapCatching {
        when {
            !it.isSuccessful -> {
                //https://github.com/square/retrofit/issues/3255
                val jsonError = JSONObject(it.errorBody()?.string())
                throw Exception(jsonError.getString("message"))
            }
            it.body() == null -> throw Exception("Card token should not be null")
            else -> it.body()!!
        }
    }.fold(::Success, ::Failure)
}
package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.data.model.body.CardHolder
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.body.IdentificationBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import com.mercadolibre.android.cardform.data.service.TokenizeService
import com.mercadolibre.android.cardform.domain.TokenizeWebCardParam
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

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
    }.mapCatching(::resolveResponse).fold(::Success, ::Failure)

    override suspend fun tokenizeWebCard(param: TokenizeWebCardParam) = runCatching {
        withContext(contextProvider.IO) {
            service.createWebCardToken(accessToken, WebCardTokenBody(
                param.cardNumberId,
                param.truncCardNumber,
                siteId,
                CardHolder(
                    IdentificationBody(param.identificationNumber, param.identificationType),
                    param.userName
                ),
                param.expirationMonth,
                param.expirationYear,
                param.cardNumberLength
            ))
        }
    }.mapCatching(::resolveResponse).fold(::Success, ::Failure)
}

private fun resolveResponse(response: Response<CardToken>): CardToken {
    return when {
        !response.isSuccessful -> {
            //https://github.com/square/retrofit/issues/3255
            val errorBody = response.errorBody()?.string()
            val errorMessage = if (errorBody.isNullOrEmpty()) {
                "unknown error"
            } else {
                val jsonError = JSONObject(errorBody)
                jsonError.getString("message")
            }
            throw Exception(errorMessage)
        }
        response.body() == null -> throw Exception("Card token should not be null")
        else -> response.body()!!
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
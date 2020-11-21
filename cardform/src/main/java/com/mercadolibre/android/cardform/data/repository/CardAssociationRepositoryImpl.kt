package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.data.model.body.*
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.domain.AssociatedCardParam
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

internal class CardAssociationRepositoryImpl(
    private val associationService: CardAssociationService,
    private val accessToken: String,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : CardAssociationRepository {

    override suspend fun associateCard(param: AssociatedCardParam) = runCatching {
        withContext(contextProvider.IO) {
            associationService.associateCard(
                BuildConfig.API_ENVIRONMENT,
                accessToken, AssociatedCardBody(
                    param.cardTokenId,
                    PaymentMethodBody(
                        param.paymentMethodId,
                        param.paymentMethodType,
                        ""
                    ),
                    IssuerBody(param.issuerId.toString())
                )
            )
        }
    }.mapCatching(::resolveResponse).fold(::Success, ::Failure)

    private fun resolveResponse(response: Response<AssociatedCard>): AssociatedCard {
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
            response.body() == null -> throw Exception("Associated card should not be null")
            else -> response.body()!!
        }
    }
}
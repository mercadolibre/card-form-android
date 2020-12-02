package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody
import com.mercadolibre.android.cardform.data.service.FinishInscriptionService
import com.mercadolibre.android.cardform.domain.FinishInscriptionRepository
import com.mercadolibre.android.cardform.domain.FinishInscriptionBusinessModel
import kotlinx.coroutines.withContext

internal class FinishInscriptionRepositoryImpl(
    private val finishInscriptionService: FinishInscriptionService,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : FinishInscriptionRepository {

    override suspend fun getFinishInscriptionData(token: String) =
        withContext(contextProvider.IO) {
            runCatching {
                finishInscriptionService
                    .getFinishInscription(TokenData(token))
                    .resolveRetrofitResponse()
            }.mapCatching {
                FinishInscriptionBusinessModel(
                    it.id,
                    it.number,
                    it.firstSixDigits,
                    it.length,
                    it.issuer.id,
                    it.paymentMethod.id,
                    it.paymentMethod.paymentTypeId,
                    it.expirationMonth,
                    it.expirationYear
                )
            }.fold(::Success, ::Failure)
        }
}

internal data class TokenData(val token: String)
internal data class Issuer(val id: Int)
internal data class FinishInscriptionData(
    val id: String,
    val firstSixDigits: String,
    val number: String,
    val expirationYear: Int,
    val expirationMonth: Int,
    val length: Int,
    val issuer: Issuer,
    val paymentMethod: PaymentMethodBody
)
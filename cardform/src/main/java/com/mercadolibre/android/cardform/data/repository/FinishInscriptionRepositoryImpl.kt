package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.mapper.FinishInscriptionBodyMapper
import com.mercadolibre.android.cardform.data.service.FinishInscriptionService
import com.mercadolibre.android.cardform.domain.FinishInscriptionRepository
import com.mercadolibre.android.cardform.domain.FinishInscriptionBusinessModel
import com.mercadolibre.android.cardform.domain.FinishInscriptionParam
import kotlinx.coroutines.withContext

internal class FinishInscriptionRepositoryImpl(
    private val accessToken: String,
    private val finishInscriptionBodyMapper: FinishInscriptionBodyMapper,
    private val finishInscriptionService: FinishInscriptionService,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : FinishInscriptionRepository {

    override suspend fun getFinishInscriptionData(param: FinishInscriptionParam) =
        withContext(contextProvider.IO) {
            runCatching {
                finishInscriptionService
                    .getFinishInscription(
                        accessToken,
                        finishInscriptionBodyMapper.map(param)
                    )
                    .resolveRetrofitResponse()
            }.mapCatching {
                FinishInscriptionBusinessModel(
                    it.cardTokenId,
                    it.firstSixDigits,
                    it.issuer.id,
                    it.paymentMethod.id,
                    it.paymentMethod.paymentTypeId
                )
            }.fold(::Success, ::Failure)
        }
}
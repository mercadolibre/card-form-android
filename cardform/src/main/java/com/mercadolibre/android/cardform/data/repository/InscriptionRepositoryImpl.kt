package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.service.InscriptionService
import com.mercadolibre.android.cardform.domain.InscriptionBusinessModel
import com.mercadolibre.android.cardform.domain.InscriptionRepository
import kotlinx.coroutines.withContext

internal class InscriptionRepositoryImpl(
    private val inscriptionService: InscriptionService,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : InscriptionRepository {

    override suspend fun getInscriptionData() =
        withContext(contextProvider.IO) {
            runCatching {
                inscriptionService
                    .getInscription()
                    .resolveRetrofitResponse()
            }.mapCatching {
                InscriptionBusinessModel(
                    it.tbkToken,
                    it.urlWebPay,
                    it.redirectUrl,
                    it.user.firstName,
                    it.user.lastName,
                    it.user.identifier?.number,
                    it.user.identifier?.type
                )
            }.fold(::Success, ::Failure)
        }
}
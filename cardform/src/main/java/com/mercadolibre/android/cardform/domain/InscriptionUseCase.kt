package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map

internal class InscriptionUseCase(
    private val inscriptionRepository: InscriptionRepository
) : UseCase<InscriptionParams, InscriptionModel>() {

    override suspend fun doExecute(param: InscriptionParams) = inscriptionRepository
        .getInscriptionData(param).map {
            val tokenData = "TBK_TOKEN=${it.token}".toByteArray()
            InscriptionModel(tokenData, it.urlWebPay)
        }
}

data class InscriptionParams(
    val userName: String,
    val userEmail: String,
    val responseUrl: String
)

data class InscriptionModel(
    val token: ByteArray,
    val urlWebPay: String
)
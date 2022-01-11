package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.repository.FinishInscriptionRepository

internal class FinishInscriptionUseCase(
    private val finishInscriptionRepository: FinishInscriptionRepository
) : UseCase<FinishInscriptionParam, FinishInscriptionModel>() {

    override suspend fun doExecute(param: FinishInscriptionParam) =
        finishInscriptionRepository.getFinishInscriptionData(param).map {
            FinishInscriptionModel(
                it.cardTokenId,
                it.firstSixDigits,
                it.issuerId,
                it.paymentMethodId,
                it.paymentMethodType
            )
        }
}

data class FinishInscriptionParam(
    val tbkToken: String,
    val userName: String,
    val identificationNumber: String?,
    val identificationType: String?
)

data class FinishInscriptionModel(
    val cardTokenId: String,
    val bin: String,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
)

data class FinishInscriptionBusinessModel(
    val cardTokenId: String,
    val firstSixDigits: String,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
)
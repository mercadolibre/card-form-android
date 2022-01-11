package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.model.request.FinishInscriptionParam
import com.mercadolibre.android.cardform.data.model.response.finishinscription.FinishInscriptionModel
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
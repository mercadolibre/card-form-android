package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map

internal class FinishInscriptionUseCase(
    private val finishInscriptionRepository: FinishInscriptionRepository
) : UseCase<String, FinishInscriptionModel>() {

    override suspend fun doExecute(param: String) =
        finishInscriptionRepository.getFinishInscriptionData(param).map {
            val lastIndexToReplace = it.firstSixDigits.lastIndex
            val truncCardNumber =
                it.lastFourDigits.replaceRange(0..lastIndexToReplace, it.firstSixDigits)

            FinishInscriptionModel(
                it.cardNumberId,
                truncCardNumber,
                "chaca",
                it.identificationNumber,
                it.identificationId,
                "12/30",
                it.cardNumberLength
            )
        }
}

data class FinishInscriptionModel(
    val cardNumberId: String,
    val truncCardNumber: String,
    val cardholderName: String,
    val identificationNumber: String,
    val identificationId: String,
    val expiration: String,
    val cardNumberLength: Int
)
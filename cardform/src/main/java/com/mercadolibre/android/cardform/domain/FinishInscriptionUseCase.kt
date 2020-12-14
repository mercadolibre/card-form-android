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
                it.expirationMonth,
                it.expirationYear,
                it.cardNumberLength,
                it.issuerId,
                it.paymentMethodId,
                it.paymentMethodType
            )
        }
}

data class FinishInscriptionModel(
    val cardNumberId: String,
    val truncCardNumber: String,
    val expirationMonth: Int,
    val expirationYear: Int,
    val cardNumberLength: Int,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
)
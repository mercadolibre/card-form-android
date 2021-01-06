package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback

internal interface FinishInscriptionRepository {
    suspend fun getFinishInscriptionData(param: FinishInscriptionParam): ResponseCallback<FinishInscriptionBusinessModel>
}

data class FinishInscriptionBusinessModel(
    val cardTokenId: String,
    val firstSixDigits: String,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String
)
package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback

internal interface FinishInscriptionRepository {
    suspend fun getFinishInscriptionData(token: String): ResponseCallback<FinishInscriptionBusinessModel>
}

data class FinishInscriptionBusinessModel(
    val cardNumberId: String,
    val lastFourDigits: String,
    val firstSixDigits: String,
    val cardNumberLength: Int,
    val issuerId: Int,
    val paymentMethodId: String,
    val paymentMethodType: String,
    val expirationMonth: Int,
    val expirationYear: Int
)
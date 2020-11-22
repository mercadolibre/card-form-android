package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback

internal interface InscriptionRepository {
    suspend fun getInscriptionData(): ResponseCallback<InscriptionBusinessModel>
}

data class InscriptionBusinessModel(
    val token: String,
    val urlWebPay: String,
    val redirectUrl: String,
    val userName: String,
    val userLastName: String,
    val identifierNumber: String,
    val identifierType: String
)
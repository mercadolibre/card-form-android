package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback

internal interface InscriptionRepository {
    suspend fun getInscriptionData(params: InscriptionParams): ResponseCallback<InscriptionBusinessModel>
}

data class InscriptionBusinessModel(
    val token: String,
    val urlWebPay: String
)
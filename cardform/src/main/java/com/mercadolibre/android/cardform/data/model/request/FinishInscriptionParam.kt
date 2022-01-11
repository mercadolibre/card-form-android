package com.mercadolibre.android.cardform.data.model.request

data class FinishInscriptionParam(
    val tbkToken: String,
    val userName: String,
    val identificationNumber: String?,
    val identificationType: String?
)
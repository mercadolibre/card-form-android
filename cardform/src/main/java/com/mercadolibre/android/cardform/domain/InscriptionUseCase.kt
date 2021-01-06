package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map

internal class InscriptionUseCase(
    private val inscriptionRepository: InscriptionRepository
) : UseCase<Unit, InscriptionModel>() {

    override suspend fun doExecute(param: Unit) = inscriptionRepository
        .getInscriptionData().map {
            val fullName = "${it.userName} ${it.userLastName}"
            InscriptionModel(
                it.token,
                it.urlWebPay,
                it.redirectUrl,
                fullName,
                it.identifierNumber,
                it.identifierType)
        }
}

data class InscriptionModel(
    val token: String,
    val urlWebPay: String,
    val redirectUrl: String,
    val fullName: String,
    val identifierNumber: String?,
    val identifierType: String?)
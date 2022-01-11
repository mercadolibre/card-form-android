package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.model.response.initinscription.InscriptionModel
import com.mercadolibre.android.cardform.data.repository.InscriptionRepository

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
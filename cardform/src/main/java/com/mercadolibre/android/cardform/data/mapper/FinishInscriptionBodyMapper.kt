package com.mercadolibre.android.cardform.data.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.body.CardHolder
import com.mercadolibre.android.cardform.data.model.body.FinishInscriptionBody
import com.mercadolibre.android.cardform.data.model.body.IdentificationBody
import com.mercadolibre.android.cardform.domain.FinishInscriptionParam

internal class FinishInscriptionBodyMapper(
    private val siteId: String
): Mapper<FinishInscriptionBody, FinishInscriptionParam> {

    override fun map(model: FinishInscriptionParam) = FinishInscriptionBody(
        siteId,
        model.token,
        CardHolder(
            IdentificationBody(
                model.identificationNumber,
                model.identificationType
            ),
            model.userName
        )
    )
}
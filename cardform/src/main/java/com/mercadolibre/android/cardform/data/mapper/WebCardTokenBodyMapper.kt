package com.mercadolibre.android.cardform.data.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.body.CardHolder
import com.mercadolibre.android.cardform.data.model.body.IdentificationBody
import com.mercadolibre.android.cardform.data.model.body.WebCardTokenBody
import com.mercadolibre.android.cardform.domain.TokenizeWebCardParam

internal class WebCardTokenBodyMapper(private val siteId: String) :
    Mapper<WebCardTokenBody, TokenizeWebCardParam> {

    override fun map(model: TokenizeWebCardParam) = WebCardTokenBody(
        model.cardNumberId,
        model.truncCardNumber,
        siteId,
        CardHolder(
            IdentificationBody(
                model.identificationNumber,
                model.identificationType
            ),
            model.userName
        ),
        model.expirationMonth,
        model.expirationYear,
        model.cardNumberLength
    )
}
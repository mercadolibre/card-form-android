package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.body.CardHolder
import com.mercadolibre.android.cardform.data.model.body.IdentificationBody
import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.presentation.model.CardStepInfo

class CardInfoMapper(private val device: Device): Mapper<CardInfoBody, CardStepInfo> {
    override fun map(model: CardStepInfo): CardInfoBody {
        val expiration = model.expiration.split('/')
        return CardInfoBody(
            model.cardNumber.replace("\\s+".toRegex(), ""),
            CardHolder(
                IdentificationBody(
                    model.identificationNumber.replace(".", ""),
                    model.identificationId
                ),
                model.nameOwner
            //TODO: refactorizar, rompe en el año 2100
            ), expiration[0].toInt(), "20${expiration[1]}".toInt(), model.code, device
        )
    }
}
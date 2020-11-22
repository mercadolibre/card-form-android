package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.body.CardHolder
import com.mercadolibre.android.cardform.data.model.body.IdentificationBody
import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.domain.FinishInscriptionModel
import com.mercadolibre.android.cardform.domain.TokenizeWebCardParam
import com.mercadolibre.android.cardform.presentation.model.CardStepInfo

internal class CardInfoMapper(private val device: Device) {

    fun map(model: CardStepInfo): CardInfoBody {
        val (expirationMonth, expirationYear) = getExpirationPair(model.expiration)
        return CardInfoBody(
            model.cardNumber.replace("\\s+".toRegex(), ""),
            CardHolder(
                IdentificationBody(
                    model.identificationNumber.replace(".", ""),
                    model.identificationId
                ),
                model.nameOwner
                //TODO: refactorizar, rompe en el año 2100
            ), expirationMonth, expirationYear, model.code, device
        )
    }

    fun map(model: FinishInscriptionModel): TokenizeWebCardParam {
        val (expirationMonth, expirationYear) = getExpirationPair(model.expiration)
        return TokenizeWebCardParam(
            model.cardNumberId,
            model.truncCardNumber,
            model.cardholderName,
            model.identificationNumber,
            model.identificationId,
            expirationMonth,
            expirationYear,
            model.cardNumberLength
        )
    }

    private fun getExpirationPair(expiration: String): Pair<Int, Int> {
        val expirationList = expiration.split('/')
        return Pair(expirationList[0].toInt(), "20${expirationList[1]}".toInt())
    }
}
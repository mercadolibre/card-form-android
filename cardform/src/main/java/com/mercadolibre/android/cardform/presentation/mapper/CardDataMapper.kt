package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.presentation.model.CardData

internal object CardDataMapper : Mapper<CardData, RegisterCard> {
    override fun map(model: RegisterCard) = CardData(
        model.cardUi,
        model.paymentMethod.paymentTypeId,
        model.paymentMethod.name,
        model.issuers.first().name,
        model.additionalSteps
    )
}
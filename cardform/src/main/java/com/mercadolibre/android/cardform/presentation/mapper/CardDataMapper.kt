package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.presentation.model.CardData

internal object CardDataMapper : Mapper<CardData, RegisterCard> {
    override fun map(model: RegisterCard) = with(model) {
        CardData(
            formTitle,
            cardUi,
            paymentMethod.paymentTypeId,
            paymentMethod.name,
            issuers.first().name,
            additionalSteps
        )
    }
}
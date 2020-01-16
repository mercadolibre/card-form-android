package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.body.IssuerBody
import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody
import com.mercadolibre.android.cardform.data.model.response.PaymentMethod

internal object CardAssociationMapper: Mapper<AssociatedCardBody, CardAssociationMapper.Model> {
    override fun map(model: Model): AssociatedCardBody {
        return AssociatedCardBody(
            model.cardTokenId,
            PaymentMethodBody(
                model.paymentMethod.paymentMethodId,
                model.paymentMethod.paymentTypeId,
                model.paymentMethod.name
            ),
            IssuerBody(model.issuerId.toString())
        )
    }

    data class Model(
        val cardTokenId: String,
        val paymentMethod: PaymentMethod,
        val issuerId: Int
    )
}
package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.presentation.model.AssociatedCardInfo
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.body.IssuerBody
import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody

object CardAssociationMapper: Mapper<AssociatedCardBody, AssociatedCardInfo> {
    override fun map(model: AssociatedCardInfo): AssociatedCardBody {
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
}
package com.mercadolibre.android.cardform.domain.model.params

internal data class AssociateCardParam(
    val cardTokenId: String,
    val paymentMethodId: String,
    val paymentMethodType: String,
    val issuerId: Int
)

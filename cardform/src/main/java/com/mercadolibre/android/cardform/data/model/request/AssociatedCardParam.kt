package com.mercadolibre.android.cardform.data.model.request

internal data class AssociatedCardParam(
    val cardTokenId: String,
    val paymentMethodId: String,
    val paymentMethodType: String,
    val issuerId: Int
)
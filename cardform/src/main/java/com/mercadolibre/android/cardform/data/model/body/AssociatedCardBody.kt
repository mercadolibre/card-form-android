package com.mercadolibre.android.cardform.data.model.body

data class AssociatedCardBody(
    val cardTokenId: String,
    val paymentMethod: PaymentMethodBody,
    val issuer: IssuerBody
)
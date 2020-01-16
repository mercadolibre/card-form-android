package com.mercadolibre.android.cardform.data.model.response

internal data class AssociatedCard(
    val dateCreated: String,
    val dateLastTimeUsed: String,
    val dateLastUpdated: String,
    val expirationMonth: Int,
    val expirationYear: Int,
    val id: String,
    val issuer: Issuer,
    val markedAsValidCard: Boolean,
    val paymentMethod: PaymentMethod,
    val siteId: String,
    val status: String,
    val userId: Int
)
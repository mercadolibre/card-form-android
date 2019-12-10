package com.mercadolibre.android.cardform.presentation.model

import com.mercadolibre.android.cardform.data.model.response.PaymentMethod

data class AssociatedCardInfo(
    val cardTokenId: String,
    val paymentMethod: PaymentMethod,
    val issuerId: Int
)
package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.data.model.response.RegisterCard

internal interface CardRepository {
    suspend fun getCardInfo(bin: String): RegisterCard?
}

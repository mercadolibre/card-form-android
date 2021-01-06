package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import com.mercadolibre.android.cardform.domain.AssociatedCardParam

internal interface CardAssociationRepository {
    suspend fun associateCard(param: AssociatedCardParam): ResponseCallback<AssociatedCard>
}
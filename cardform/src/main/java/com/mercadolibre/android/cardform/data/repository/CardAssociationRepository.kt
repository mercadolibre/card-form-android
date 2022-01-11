package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.data.model.request.AssociatedCardParam
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard

internal interface CardAssociationRepository {
    suspend fun associateCard(param: AssociatedCardParam): ResponseCallback<AssociatedCard>
}
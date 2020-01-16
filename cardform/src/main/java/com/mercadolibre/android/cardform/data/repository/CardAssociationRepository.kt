package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody

internal interface CardAssociationRepository {
    suspend fun associateCard(associatedCardBody: AssociatedCardBody): AssociatedCard?
}
package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.data.model.response.AssociatedCardDM
import com.mercadolibre.android.cardform.domain.model.params.AssociateCardParam

internal interface CardAssociationRepository {
    suspend fun associateCard(param: AssociateCardParam): ResponseCallback<AssociatedCardDM>
}

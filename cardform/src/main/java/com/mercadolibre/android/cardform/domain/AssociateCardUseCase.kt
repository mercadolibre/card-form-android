package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.repository.CardAssociationRepository
import com.mercadolibre.android.cardform.domain.model.AssociatedCardBM
import com.mercadolibre.android.cardform.domain.model.params.AssociateCardParam

internal class AssociateCardUseCase(
    private val cardAssociationRepository: CardAssociationRepository
) : UseCase<AssociateCardParam, AssociatedCardBM>() {

    override suspend fun doExecute(param: AssociateCardParam) = cardAssociationRepository.associateCard(param).map {
        AssociatedCardBM(it.id, it.enrollmentSuggested)
    }
}

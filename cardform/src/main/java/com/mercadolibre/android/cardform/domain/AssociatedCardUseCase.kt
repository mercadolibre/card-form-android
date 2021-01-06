package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.repository.CardAssociationRepository

internal class AssociatedCardUseCase(
    private val cardAssociationRepository: CardAssociationRepository
) : UseCase<AssociatedCardParam, String>() {

    override suspend fun doExecute(param: AssociatedCardParam) =
        cardAssociationRepository.associateCard(param).map { associatedCard -> associatedCard.id }
}

internal data class AssociatedCardParam(
    val cardTokenId: String,
    val paymentMethodId: String,
    val paymentMethodType: String,
    val issuerId: Int
)
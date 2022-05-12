package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.domain.AssociateCardUseCase
import com.mercadolibre.android.cardform.domain.CardTokenUseCase
import com.mercadolibre.android.cardform.domain.FinishInscriptionUseCase
import com.mercadolibre.android.cardform.domain.InscriptionUseCase
import com.mercadolibre.android.cardform.domain.TokenDeviceUseCase

internal class UseCaseModule(cardForm: CardForm, repositoryModule: RepositoryModule) {
    val cardTokenUseCase: CardTokenUseCase = CardTokenUseCase(repositoryModule.tokenizeRepository)
    val cardAssociationUseCase: AssociateCardUseCase =
        AssociateCardUseCase(repositoryModule.cardAssociationRepository)
    val inscriptionUseCase: InscriptionUseCase =
        InscriptionUseCase(repositoryModule.inscriptionRepository)
    val finishInscriptionUseCase: FinishInscriptionUseCase =
        FinishInscriptionUseCase(repositoryModule.finishInscriptionRepository)
    val tokenDeviceUseCase = TokenDeviceUseCase(
        cardForm.tokenizationFlowEnabled,
        repositoryModule.tokenDeviceRepository
    )
}

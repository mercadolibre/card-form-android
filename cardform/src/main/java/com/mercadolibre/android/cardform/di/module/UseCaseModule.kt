package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.domain.AssociatedCardUseCase
import com.mercadolibre.android.cardform.domain.FinishInscriptionUseCase
import com.mercadolibre.android.cardform.domain.InscriptionUseCase
import com.mercadolibre.android.cardform.domain.TokenizeUseCase
import com.mercadolibre.android.cardform.domain.TokenizeWebCardUseCase

internal class UseCaseModule(repositoryModule: RepositoryModule) {
    val tokenizeUseCase: TokenizeUseCase = TokenizeUseCase(repositoryModule.tokenizeRepository)
    val cardAssociationUseCase: AssociatedCardUseCase = AssociatedCardUseCase(repositoryModule.cardAssociationRepository)
    val inscriptionUseCase: InscriptionUseCase = InscriptionUseCase(repositoryModule.inscriptionRepository)
    val finishInscriptionUseCase: FinishInscriptionUseCase = FinishInscriptionUseCase(repositoryModule.finishInscriptionRepository)
    val tokenizeWebCardUseCase: TokenizeWebCardUseCase = TokenizeWebCardUseCase(repositoryModule.tokenizeRepository)
}
package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.domain.AssociatedCardUseCase
import com.mercadolibre.android.cardform.domain.TokenizeUseCase

internal class UseCaseModule(repositoryModule: RepositoryModule) {
    val tokenizeUseCase: TokenizeUseCase = TokenizeUseCase(repositoryModule.tokenizeRepository)
    val cardAssociationUseCase: AssociatedCardUseCase = AssociatedCardUseCase(repositoryModule.cardAssociationRepository)
}
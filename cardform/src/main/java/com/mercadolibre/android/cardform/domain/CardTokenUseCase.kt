package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.domain.model.CardTokenBM

internal class CardTokenUseCase(
    private val cardTokenRepository: CardTokenRepository
) : UseCase<CardInfoBody, CardTokenBM>() {

    override suspend fun doExecute(param: CardInfoBody) =
        cardTokenRepository.get(param).map { CardTokenBM(it.id, it.esc, it.lastFourDigits) }
}

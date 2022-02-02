package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.tokenize.CardTokenModel
import com.mercadolibre.android.cardform.data.repository.TokenizeRepository

internal class TokenizeUseCase(
    private val tokenizeRepository: TokenizeRepository
) : UseCase<CardInfoBody, CardTokenModel>() {

    override suspend fun doExecute(param: CardInfoBody) =
        tokenizeRepository.tokenizeCard(param).map { CardTokenModel(it.id, it.esc.orEmpty(), it.lastFourDigits) }
}
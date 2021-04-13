package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.repository.TokenizeRepository

internal class TokenizeUseCase(
    private val tokenizeRepository: TokenizeRepository
) : UseCase<CardInfoBody, CardTokenModel>() {

    override suspend fun doExecute(param: CardInfoBody) =
        tokenizeRepository.tokenizeCard(param).map { CardTokenModel(it.id, it.esc.orEmpty(), param.cardNumber, "" + param.expirationMonth + param.expirationYear, param.securityCode) }
}

data class CardTokenModel(
    val id: String,
    val esc: String,
    val pan: String,
    val exp: String,
    val cvv: String
)
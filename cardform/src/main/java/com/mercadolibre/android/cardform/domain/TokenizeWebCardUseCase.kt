package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.UseCase
import com.mercadolibre.android.cardform.base.map
import com.mercadolibre.android.cardform.data.repository.TokenizeRepository

internal class TokenizeWebCardUseCase(private val tokenizeRepository: TokenizeRepository) :
    UseCase<TokenizeWebCardParam, String>() {

    override suspend fun doExecute(param: TokenizeWebCardParam) =
        tokenizeRepository.tokenizeWebCard(param).map { it.id }
}

internal data class TokenizeWebCardParam(
    val cardNumberId: String,
    val truncCardNumber: String,
    val userName: String,
    val identificationNumber: String,
    val identificationType: String,
    val expirationMonth: Int,
    val expirationYear: Int,
    val cardNumberLength: Int
)
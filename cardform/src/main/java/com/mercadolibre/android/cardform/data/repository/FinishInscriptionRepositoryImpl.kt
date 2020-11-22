package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.data.service.FinishInscriptionService
import com.mercadolibre.android.cardform.domain.FinishInscriptionRepository
import com.mercadolibre.android.cardform.domain.FinishInscriptionBusinessModel
import kotlinx.coroutines.withContext

internal class FinishInscriptionRepositoryImpl(
    private val finishInscriptionService: FinishInscriptionService,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : FinishInscriptionRepository {

    override suspend fun getFinishInscriptionData(token: String) = runCatching {
        withContext(contextProvider.IO) {
            finishInscriptionService.getFinishInscription(TokenData(token))
        }
    }.mapCatching {

        if (it.errorMessage.isNotEmpty()) {
            throw Exception(it.errorMessage)
        }

        FinishInscriptionBusinessModel(
            it.tbkUser,
            it.cardNumber,
            it.bin,
            it.cardNumberLength,
            it.identificationNumber ?: "153856400",
            it.identificationId ?: "rut"
        )

    }.fold(::Success, ::Failure)
}

data class TokenData(val token: String)

data class FinishInscriptionData(
    val responseCode: Int,
    val tbkUser: String,
    val authorizationCode: String,
    val cardType: String,
    val cardNumber: String,
    val errorMessage: String,
    val bin: String,
    val cardNumberLength: Int,
    val issuerId: Int,
    val identificationNumber: String?,
    val identificationId: String?
)
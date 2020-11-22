package com.mercadolibre.android.cardform.data.repository

import com.google.gson.annotations.SerializedName
import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.data.service.InscriptionService
import com.mercadolibre.android.cardform.domain.InscriptionBusinessModel
import com.mercadolibre.android.cardform.domain.InscriptionParams
import com.mercadolibre.android.cardform.domain.InscriptionRepository
import kotlinx.coroutines.withContext

internal class InscriptionRepositoryImpl(
    private val inscriptionService: InscriptionService,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : InscriptionRepository {

    override suspend fun getInscriptionData(params: InscriptionParams) = runCatching {
        withContext(contextProvider.IO) {
            inscriptionService.getInscription(
                InscriptionBody(
                    params.userName,
                    params.userEmail,
                    params.responseUrl
                )
            )
        }
    }.mapCatching {
        if (it.errorMessage.isNotEmpty()) {
            throw Exception(it.errorMessage)
        }
        InscriptionBusinessModel(it.token, it.urlWebPay)
    }.fold(::Success, ::Failure)
}

data class InscriptionBody(val username: String, val email: String, val responseUrl: String)

data class InscriptionDataModel(
    val token: String,
    @SerializedName("url_webpay")
    val urlWebPay: String,
    val errorMessage: String
)
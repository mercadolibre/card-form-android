package com.mercadolibre.android.cardform.data.repository

import com.google.gson.annotations.SerializedName
import com.mercadolibre.android.cardform.base.BaseCoroutine
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.service.InscriptionService
import com.mercadolibre.android.cardform.domain.InscriptionBusinessModel
import com.mercadolibre.android.cardform.domain.InscriptionRepository
import kotlinx.coroutines.withContext

internal class InscriptionRepositoryImpl(
    private val accessToken: String,
    private val inscriptionService: InscriptionService
) : BaseCoroutine(), InscriptionRepository {

    override suspend fun getInscriptionData() =
        withContext(contextProvider.IO) {
            runCatching {
                inscriptionService
                    .getInscription(accessToken = accessToken)
                    .resolveRetrofitResponse()
            }.mapCatching {
                InscriptionBusinessModel(
                    it.tbkToken,
                    it.urlWebPay,
                    it.redirectUrl,
                    it.user.firstName,
                    it.user.lastName,
                    it.user.identifier.number,
                    it.user.identifier.type
                )
            }.fold(::Success, ::Failure)
        }
}

internal data class InscriptionDataModel(
    val tbkToken: String,
    @SerializedName("url_webpay")
    val urlWebPay: String,
    val redirectUrl: String,
    val user: User
)

internal data class User(
    val firstName: String,
    val lastName: String,
    val identifier: Identifier
)

internal data class Identifier(
    val number: String,
    val type: String
)
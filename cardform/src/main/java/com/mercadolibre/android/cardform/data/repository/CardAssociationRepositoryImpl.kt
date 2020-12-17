package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.model.body.*
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.domain.AssociatedCardParam
import kotlinx.coroutines.withContext

internal class CardAssociationRepositoryImpl(
    private val associationService: CardAssociationService,
    private val accessToken: String,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : CardAssociationRepository {

    override suspend fun associateCard(param: AssociatedCardParam) =
        withContext(contextProvider.IO) {
            runCatching {
                associationService.associateCard(
                    accessToken,
                    AssociatedCardBody(
                        param.cardTokenId,
                        PaymentMethodBody(
                            param.paymentMethodId,
                            param.paymentMethodType
                        ),
                        IssuerBody(param.issuerId.toString())
                    )
                ).resolveRetrofitResponse()
            }.fold(::Success, ::Failure)
        }
}
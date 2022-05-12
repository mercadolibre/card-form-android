package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import com.mercadolibre.android.cardform.base.Response.Failure
import com.mercadolibre.android.cardform.base.Response.Success
import com.mercadolibre.android.cardform.base.resolveRetrofitResponse
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.body.Features
import com.mercadolibre.android.cardform.data.model.body.IssuerBody
import com.mercadolibre.android.cardform.data.model.body.PaymentMethodBody
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.domain.model.params.AssociateCardParam
import kotlinx.coroutines.withContext

internal class CardAssociationRepositoryImpl(
    private val associationService: CardAssociationService,
    private val acceptThirdPartyCard: Boolean,
    private val activateCard: Boolean,
    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : CardAssociationRepository {

    override suspend fun associateCard(param: AssociateCardParam) =
        withContext(contextProvider.IO) {
            runCatching {
                associationService.associateCard(
                    AssociatedCardBody(
                        param.cardTokenId,
                        PaymentMethodBody(
                            param.paymentMethodId,
                            param.paymentMethodType
                        ),
                        IssuerBody(param.issuerId.toString()),
                        Features(
                            acceptThirdPartyCard,
                            activateCard
                        )
                    )
                ).resolveRetrofitResponse()
            }.fold(::Success, ::Failure)
        }
}

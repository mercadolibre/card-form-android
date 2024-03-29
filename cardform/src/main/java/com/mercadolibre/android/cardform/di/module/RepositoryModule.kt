package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.data.mapper.FinishInscriptionBodyMapper
import com.mercadolibre.android.cardform.data.repository.*
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.data.service.FinishInscriptionService
import com.mercadolibre.android.cardform.data.service.InscriptionService
import com.mercadolibre.android.cardform.data.service.TokenizeService
import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import retrofit2.Retrofit

internal class RepositoryModule(
        retrofit: Retrofit,
        siteId: String,
        excludedPaymentTypes: List<String>?,
        flowId: String,
        cardInfo: CardInfoDto?,
        acceptThirdPartyCard: Boolean,
        activateCard: Boolean
) {
    val cardRepository by lazy {
        CardRepositoryImpl(retrofit.create(CardService::class.java), siteId, excludedPaymentTypes, flowId, cardInfo)
    }
    val tokenizeRepository by lazy {
        TokenizeRepositoryImpl(retrofit.create(TokenizeService::class.java))
    }
    val cardAssociationRepository by lazy {
        CardAssociationRepositoryImpl(
            retrofit.create(CardAssociationService::class.java),
            acceptThirdPartyCard, activateCard
        )
    }
    val inscriptionRepository by lazy {
        InscriptionRepositoryImpl(
            retrofit.create(InscriptionService::class.java)
        )
    }

    val finishInscriptionRepository by lazy {
        FinishInscriptionRepositoryImpl(
            FinishInscriptionBodyMapper(siteId),
            retrofit.create(FinishInscriptionService::class.java)
        )
    }
}
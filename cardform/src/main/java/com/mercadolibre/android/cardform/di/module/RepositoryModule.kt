package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.data.mapper.FinishInscriptionBodyMapper
import com.mercadolibre.android.cardform.data.repository.*
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.data.service.FinishInscriptionService
import com.mercadolibre.android.cardform.data.service.InscriptionService
import com.mercadolibre.android.cardform.data.service.TokenizeService
import retrofit2.Retrofit

internal class RepositoryModule(
    retrofit: Retrofit,
    accessToken: String,
    siteId: String,
    excludedPaymentTypes: List<String>?
) {
    val cardRepository by lazy {
        CardRepositoryImpl(retrofit.create(CardService::class.java), siteId, excludedPaymentTypes)
    }
    val tokenizeRepository by lazy {
        TokenizeRepositoryImpl(accessToken, retrofit.create(TokenizeService::class.java))
    }
    val cardAssociationRepository by lazy {
        CardAssociationRepositoryImpl(
            retrofit.create(CardAssociationService::class.java),
            accessToken
        )
    }
    val inscriptionRepository by lazy {
        InscriptionRepositoryImpl(
            accessToken,
            retrofit.create(InscriptionService::class.java)
        )
    }

    val finishInscriptionRepository by lazy {
        FinishInscriptionRepositoryImpl(
            accessToken,
            FinishInscriptionBodyMapper(siteId),
            retrofit.create(FinishInscriptionService::class.java)
        )
    }
}
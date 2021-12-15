package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.data.mapper.FinishInscriptionBodyMapper
import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import com.mercadolibre.android.cardform.data.repository.CardAssociationRepository
import com.mercadolibre.android.cardform.data.repository.CardAssociationRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.CardRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.CardTokenRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.FinishInscriptionRepository
import com.mercadolibre.android.cardform.data.repository.FinishInscriptionRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.InscriptionRepository
import com.mercadolibre.android.cardform.data.repository.InscriptionRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.TokenDeviceRepositoryImpl
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.data.service.FinishInscriptionService
import com.mercadolibre.android.cardform.data.service.InscriptionService
import com.mercadolibre.android.cardform.data.service.TokenizeService
import com.mercadolibre.android.cardform.domain.CardRepository
import com.mercadolibre.android.cardform.domain.CardTokenRepository
import com.mercadolibre.android.cardform.domain.TokenDeviceRepository
import retrofit2.Retrofit

internal class RepositoryModule(
    retrofit: Retrofit,
    siteId: String,
    excludedPaymentTypes: List<String>?,
    flowId: String,
    sessionId: String,
    cardInfo: CardInfoDto?,
    acceptThirdPartyCard: Boolean,
    activateCard: Boolean,
    behaviourModule: BehaviourModule
) {
    val cardRepository: CardRepository by lazy {
        CardRepositoryImpl(retrofit.create(CardService::class.java), siteId, excludedPaymentTypes, flowId, cardInfo)
    }

    val tokenizeRepository: CardTokenRepository by lazy {
        CardTokenRepositoryImpl(retrofit.create(TokenizeService::class.java))
    }

    val cardAssociationRepository: CardAssociationRepository by lazy {
        CardAssociationRepositoryImpl(
            retrofit.create(CardAssociationService::class.java),
            acceptThirdPartyCard, activateCard
        )
    }

    val inscriptionRepository: InscriptionRepository by lazy {
        InscriptionRepositoryImpl(retrofit.create(InscriptionService::class.java))
    }

    val finishInscriptionRepository: FinishInscriptionRepository by lazy {
        FinishInscriptionRepositoryImpl(
            FinishInscriptionBodyMapper(siteId),
            retrofit.create(FinishInscriptionService::class.java)
        )
    }

    val tokenDeviceRepository: TokenDeviceRepository by lazy {
        TokenDeviceRepositoryImpl(flowId, sessionId, behaviourModule.tokenDeviceBehaviour)
    }
}

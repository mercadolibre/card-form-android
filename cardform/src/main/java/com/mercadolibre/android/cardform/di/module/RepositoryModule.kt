package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.data.repository.CardAssociationRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.CardRepositoryImpl
import com.mercadolibre.android.cardform.data.repository.TokenizeRepositoryImpl
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.data.service.TokenizeService
import retrofit2.Retrofit
import java.io.Serializable

internal class RepositoryModule(
    retrofit: Retrofit, accessToken: String,
    siteId: String,
    excludedPaymentTypes: List<String>?,
    flowId: String,
    extraData: Serializable?
) {
    val cardRepository by lazy {
        CardRepositoryImpl(retrofit.create(CardService::class.java), siteId, excludedPaymentTypes, flowId, extraData)
    }
    val tokenizeRepository by lazy {
        TokenizeRepositoryImpl(retrofit.create(TokenizeService::class.java), accessToken)
    }
    val cardAssociation by lazy {
        CardAssociationRepositoryImpl(retrofit.create(CardAssociationService::class.java), accessToken)
    }
}
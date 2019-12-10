package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.data.repository.*
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.data.service.TokenizeService
import retrofit2.Retrofit

class RepositoryModule(private val retrofit: Retrofit, private val accessToken: String,
                       private val siteId: String, private val excludedPaymentTypes: List<String>?) {
    val cardRepository by lazy {
        CardRepositoryImpl(retrofit.create(CardService::class.java), siteId, excludedPaymentTypes)
    }
    val tokenizeRepository by lazy {
        TokenizeRepositoryImpl(retrofit.create(TokenizeService::class.java), accessToken)
    }
    val cardAssociation by lazy {
        CardAssociationRepositoryImpl(retrofit.create(CardAssociationService::class.java), accessToken)
    }
}
package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardAssociationRepositoryImpl(private val associationService: CardAssociationService,
                                    private val accessToken: String) : CardAssociationRepository {
    override suspend fun associateCard(associatedCardBody: AssociatedCardBody): AssociatedCard? {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                val response = associationService.associateCardAsync(BuildConfig.API_ENVIRONMENT,
                    accessToken, associatedCardBody).await()
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

internal class CardAssociationRepositoryImpl(private val associationService: CardAssociationService,
                                    private val accessToken: String) : CardAssociationRepository {
    override suspend fun associateCard(associatedCardBody: AssociatedCardBody): AssociatedCard? {
        return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                with(associationService.associateCardAsync(BuildConfig.API_ENVIRONMENT,
                    accessToken, associatedCardBody)) {
                    if (isSuccessful) {
                        body()
                    } else {
                        //https://github.com/square/retrofit/issues/3255
                        val jsonError = JSONObject(errorBody()?.string())
                        throw IOException(jsonError.getString("message"))
                    }
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

private const val ENVIRONMENT = BuildConfig.API_ENVIRONMENT

internal interface CardAssociationService {
    @POST("/$ENVIRONMENT/px_mobile/v1/card")
    suspend fun associateCard(
        @Body associatedCardBody: AssociatedCardBody
    ): Response<AssociatedCard>
}
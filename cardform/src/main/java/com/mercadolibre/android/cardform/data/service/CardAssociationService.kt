package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

private const val ENVIRONMENT = BuildConfig.API_ENVIRONMENT

internal interface CardAssociationService {
    @POST("https://run.mocky.io/v3/40de3384-92da-4b7f-bd63-1e1a0d78b1c9")
    suspend fun associateCard(
        @Query("access_token") accessToken: String,
        @Body associatedCardBody: AssociatedCardBody
    ): Response<AssociatedCard>
}
package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.response.AssociatedCard
import com.mercadolibre.android.cardform.data.model.body.AssociatedCardBody
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CardAssociationService {
    @POST("/{environment}/px_mobile/v1/card")
    fun associateCardAsync(
        @Path("environment") environment : String,
        @Query("access_token") accessToken : String,
        @Body associatedCardBody: AssociatedCardBody
    ): Deferred<Response<AssociatedCard?>>
}
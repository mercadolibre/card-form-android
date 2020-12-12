package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.repository.InscriptionDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface InscriptionService {
    @GET("/{environment}/px_mobile/v1/card_webpay/inscription/init")
    suspend fun getInscription(
        @Path("environment") environment : String = BuildConfig.API_ENVIRONMENT,
        @Query("access_token") accessToken : String
    ): Response<InscriptionDataModel>
}
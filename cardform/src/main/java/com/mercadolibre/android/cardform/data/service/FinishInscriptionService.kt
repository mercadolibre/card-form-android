package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.FinishInscriptionData
import com.mercadolibre.android.cardform.data.repository.TokenData
import retrofit2.Response
import retrofit2.http.*

internal interface FinishInscriptionService {
    @POST("/{environment}/px_mobile/v1/card_webpay/inscription/finish")
    suspend fun getFinishInscription(
        @Path("environment") environment : String,
        @Query("access_token") accessToken: String,
        @Body token: TokenData
    ): Response<FinishInscriptionData>
}
package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.body.TokenData
import com.mercadolibre.android.cardform.data.model.response.finishinscription.FinishInscriptionData
import retrofit2.Response
import retrofit2.http.*

private const val ENVIRONMENT = BuildConfig.API_ENVIRONMENT

internal interface FinishInscriptionService {
    @POST("/$ENVIRONMENT/px_mobile/v1/card_webpay/inscription/finish")
    suspend fun getFinishInscription(
        @Query("access_token") accessToken: String,
        @Body token: TokenData
    ): Response<FinishInscriptionData>
}
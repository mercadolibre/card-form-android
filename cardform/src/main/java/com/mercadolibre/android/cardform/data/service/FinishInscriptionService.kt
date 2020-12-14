package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.repository.FinishInscriptionData
import com.mercadolibre.android.cardform.data.repository.TokenData
import retrofit2.Response
import retrofit2.http.*

internal interface FinishInscriptionService {
    @POST("http://api.mp.internal.ml.com/alpha/px_mobile/v1/card_webpay/inscription/finish")
    suspend fun getFinishInscription(
        @Query("access_token") accessToken: String,
        @Body token: TokenData,
        @Header("X-public") public: Boolean = true
    ): Response<FinishInscriptionData>
}
package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.InscriptionDataModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

internal interface InscriptionService {
    @GET("http://api.mp.internal.ml.com/gamma/px_mobile/v1/card_webpay/inscription/init")
    suspend fun getInscription(
        @Query("access_token") accessToken : String,
        @Header("X-public") public: Boolean = true
    ): InscriptionDataModel
}
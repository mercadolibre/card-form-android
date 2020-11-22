package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.InscriptionDataModel
import retrofit2.http.POST
import retrofit2.http.Query

internal interface InscriptionService {
    @POST("https://run.mocky.io/v3/6b955fe3-445a-4098-97f1-a35f87b8658b")
    suspend fun getInscription(
        @Query("access_token") accessToken : String
    ): InscriptionDataModel
}
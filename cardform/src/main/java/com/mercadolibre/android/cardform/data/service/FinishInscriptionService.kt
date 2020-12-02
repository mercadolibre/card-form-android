package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.FinishInscriptionData
import com.mercadolibre.android.cardform.data.repository.TokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface FinishInscriptionService {
    @POST("https://run.mocky.io/v3/ee1f871a-4ce8-40bb-9c18-4840317decc1")
    suspend fun getFinishInscription(@Body token: TokenData): Response<FinishInscriptionData>
}
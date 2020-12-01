package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.FinishInscriptionData
import com.mercadolibre.android.cardform.data.repository.TokenData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface FinishInscriptionService {
    @POST("https://run.mocky.io/v3/88452122-6b45-4baa-87aa-8b6872903914")
    suspend fun getFinishInscription(@Body token: TokenData): Response<FinishInscriptionData>
}
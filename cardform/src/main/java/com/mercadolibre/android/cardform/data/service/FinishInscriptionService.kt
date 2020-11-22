package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.FinishInscriptionData
import com.mercadolibre.android.cardform.data.repository.TokenData
import retrofit2.http.Body
import retrofit2.http.POST

internal interface FinishInscriptionService {
    @POST("http://api.mp.internal.ml.com/g2/staging/integration/transbank-webpay-oneclick/finish_inscription")
    suspend fun getFinishInscription(@Body token: TokenData): FinishInscriptionData
}
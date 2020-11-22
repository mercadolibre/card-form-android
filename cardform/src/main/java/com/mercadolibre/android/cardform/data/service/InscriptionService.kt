package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.repository.InscriptionBody
import com.mercadolibre.android.cardform.data.repository.InscriptionDataModel
import retrofit2.http.Body
import retrofit2.http.POST

internal interface InscriptionService {
    @POST("http://api.mp.internal.ml.com/g2/staging/integration/transbank-webpay-oneclick/inscription")
    suspend fun getInscription(@Body body: InscriptionBody): InscriptionDataModel
}
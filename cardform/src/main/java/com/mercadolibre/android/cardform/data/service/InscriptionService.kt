package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.response.initinscription.InscriptionDataModel
import retrofit2.Response
import retrofit2.http.GET

private const val ENVIRONMENT = BuildConfig.API_ENVIRONMENT

internal interface InscriptionService {

    @GET("/$ENVIRONMENT/px_mobile/v1/card_webpay/inscription/init")
    suspend fun getInscription(
    ): Response<InscriptionDataModel>
}
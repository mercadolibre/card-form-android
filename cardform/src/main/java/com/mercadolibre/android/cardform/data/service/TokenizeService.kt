package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardTokenDM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface TokenizeService {

    @POST("/v1/card_tokens/${BuildConfig.GATEWAY_ENVIRONMENT}")
    suspend fun createTokenAsync(
        @Body cardInfoBody: CardInfoBody
    ): Response<CardTokenDM>
}

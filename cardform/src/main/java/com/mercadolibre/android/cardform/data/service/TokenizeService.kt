package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface TokenizeService {
    @POST("/v1/card_tokens")
    suspend fun createTokenAsync(
        @Body cardInfoBody: CardInfoBody
    ): Response<CardToken>
}
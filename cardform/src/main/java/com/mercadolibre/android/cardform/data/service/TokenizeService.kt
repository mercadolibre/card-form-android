package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

internal interface TokenizeService {
    ///todo
    @POST("/v1/card_tokens/zeta")
    suspend fun createTokenAsync(
        @Query("access_token") accessToken : String,
        @Body cardInfoBody: CardInfoBody
    ): Response<CardToken>
}
package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface TokenizeService {
    @POST("/v1/card_tokens")
    fun createTokenAsync(
        @Query("access_token") accessToken : String,
        @Body cardInfoBody: CardInfoBody
    ): Deferred<Response<CardToken>>
}
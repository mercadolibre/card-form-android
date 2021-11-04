package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import retrofit2.Response
import retrofit2.http.*

internal interface CardService {

    @GET("/gamma/px_mobile/v1/card")
    suspend fun getCardInfoAsync(
        //@Path("environment") environment : String,
        @Query("bin") bin : String,
        @Query("site_id") siteId : String,
        @Query("excluded_payment_types") excludedPaymentTypes : List<String>? = null,
        @Query("odr") odrFlag : Boolean = true
    ): Response<RegisterCard>

    @POST("/gamma/px_mobile/v1/card/marketplace")
    suspend fun getCardInfoAsyncFromMarketplace(
            //@Path("environment") environment : String,
            @Body cardInfo: CardInfoDto
    ): Response<RegisterCard>
}
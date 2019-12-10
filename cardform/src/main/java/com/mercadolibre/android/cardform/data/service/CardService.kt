package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardService {

    @GET("/{environment}/px_mobile/v1/card")
    fun getCardInfoAsync(
        @Path("environment") environment : String,
        @Query("bin") bin : String,
        @Query("site_id") siteId : String,
        @Query("excluded_payment_types") excludedPaymentTypes : List<String>? = null
    ): Deferred<Response<RegisterCard>>
}
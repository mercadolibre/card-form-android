package com.mercadolibre.android.cardform.data.service

import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.Serializable

internal interface CardService {

    @POST("/{environment}/px_mobile/v1/card")
    suspend fun getCardInfoAsync(
        @Path("environment") environment: String,
        @Body cardInfo: CardInfoDto
    ): Response<RegisterCard>
}

data class CardInfoDto(
    val bin: String,
    val siteId: String,
    val excludedPaymentTypes: List<String>? = null,
    val flowId: String,
    val extraData: Serializable? = null
)
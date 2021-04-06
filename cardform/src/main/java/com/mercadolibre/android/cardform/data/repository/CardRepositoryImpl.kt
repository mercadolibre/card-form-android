package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.CardInfoDto
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.network.exceptions.ExcludePaymentException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.json.JSONObject
import java.io.IOException

internal class CardRepositoryImpl(
    private val cardService: CardService,
    private val siteId: String,
    private val excludedPaymentTypes: List<String>?,
    private val flowId: String,
    private val cardInfo: CardInfoDto?
) : CardRepository {

    private val cache = mutableMapOf<String, RegisterCard>()
    private val queue = mutableListOf<String>()

    @Synchronized override suspend fun getCardInfo(bin: String):
            RegisterCard? {
        if (queue.contains(bin)) {
            return null
        }
        cache[bin]?.let {
            return it
        }
        queue.add(bin)
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val response = if (flowId.startsWith("checkout-on") && cardInfo != null) {
                    cardInfo.bin = bin
                    cardService.getCardInfoAsyncFromMarketplace(BuildConfig.API_ENVIRONMENT, cardInfo!!)
                } else {
                    cardService.getCardInfoAsync(BuildConfig.API_ENVIRONMENT, bin,
                            siteId, excludedPaymentTypes)
                }

                response.run {
                    if (isSuccessful) {
                        body()?.let {
                            cache[bin] = it
                            queue.remove(bin)
                            return@async it
                        }
                    } else {
                        //https://github.com/square/retrofit/issues/3255
                        val jsonObject = JSONObject(errorBody()?.string())
                        if (code() == 400) {
                            throw ExcludePaymentException(jsonObject)
                        } else {
                            val message = jsonObject.getString("message")
                            throw IOException(message)
                        }
                    }
                }
            } catch (e: Exception) {
                queue.remove(bin)
                throw e
            }
        }.await()
    }
}
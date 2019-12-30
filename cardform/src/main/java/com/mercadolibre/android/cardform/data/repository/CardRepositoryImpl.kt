package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.BuildConfig
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.service.CardService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException

class CardRepositoryImpl(private val cardService: CardService, private val siteId: String,
                         private val excludedPaymentTypes: List<String>?) : CardRepository {

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
                val response = cardService.getCardInfoAsync(BuildConfig.API_ENVIRONMENT, bin,
                    siteId, excludedPaymentTypes).await()
                response.run {
                    if (isSuccessful) {
                        body()?.let {
                            cache[bin] = it
                            queue.remove(bin)
                            return@async it
                        }
                    } else {
                        throw IOException()
                    }
                }
            } catch (e: Exception) {
                queue.remove(bin)
                throw e
            }
        }.await()
    }
}
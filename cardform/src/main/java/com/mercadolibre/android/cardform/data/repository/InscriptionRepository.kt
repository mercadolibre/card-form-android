package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.domain.InscriptionBusinessModel

internal interface InscriptionRepository {
    suspend fun getInscriptionData(): ResponseCallback<InscriptionBusinessModel>
}
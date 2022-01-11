package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.domain.FinishInscriptionBusinessModel
import com.mercadolibre.android.cardform.domain.FinishInscriptionParam

internal interface FinishInscriptionRepository {
    suspend fun getFinishInscriptionData(param: FinishInscriptionParam): ResponseCallback<FinishInscriptionBusinessModel>
}
package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.data.model.request.FinishInscriptionParam
import com.mercadolibre.android.cardform.data.model.response.finishinscription.FinishInscriptionBusinessModel

internal interface FinishInscriptionRepository {
    suspend fun getFinishInscriptionData(param: FinishInscriptionParam): ResponseCallback<FinishInscriptionBusinessModel>
}
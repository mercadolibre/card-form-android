package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.base.Response
import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.domain.TokenDeviceRepository
import com.mercadopago.android.px.addons.TokenDeviceBehaviour
import com.mercadopago.android.px.addons.tokenization.Tokenize

internal class TokenDeviceRepositoryImpl(
    private val flowId: String,
    private val sessionId: String,
    private val tokenDeviceBehaviour: TokenDeviceBehaviour
) : TokenDeviceRepository {

    override val isFeatureAvailable: Boolean
        get() = tokenDeviceBehaviour.isFeatureAvailable

    override fun get(cardId: String): ResponseCallback<Tokenize> {
        return Response.Success(tokenDeviceBehaviour.getTokenize(flowId, cardId).sessionId(sessionId))
    }
}

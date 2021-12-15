package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.base.ResponseCallback
import com.mercadolibre.android.cardform.base.UseCase
import com.mercadopago.android.px.addons.tokenization.Tokenize

internal class TokenDeviceUseCase(
    private val tokenizationFlowEnabled: Boolean,
    private val tokenDeviceRepository: TokenDeviceRepository
) : UseCase<String, Tokenize>() {

    override suspend fun doExecute(param: String): ResponseCallback<Tokenize> {
        return if (tokenizationFlowEnabled && tokenDeviceRepository.isFeatureAvailable) {
            tokenDeviceRepository.get(param)
        } else {
            error("token device feature is not available")
        }
    }
}

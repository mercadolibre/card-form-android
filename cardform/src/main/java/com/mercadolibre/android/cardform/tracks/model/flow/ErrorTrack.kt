package com.mercadolibre.android.cardform.tracks.model.flow

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData
import com.mercadolibre.android.cardform.tracks.model.TrackApiSteps

internal class ErrorTrack(
    private val errorStep: String,
    private val errorMessage: String,
    private val bin: String = "",
    private val issuer: Int = 0,
    private val paymentMethodId: String = "",
    private val paymentMethodType: String = ""
) : TrackData {
    override val pathEvent = "${BASE_PATH}/error"

    override fun addTrackData(data: MutableMap<String, Any>) {
        data[ERROR_STEP] = errorStep
        data[ERROR_MESSAGE] = errorMessage
        if (validStepError()) {
            data[BIN] = bin
            data[ISSUER] = issuer
            data[PAYMENT_METHOD_ID] = paymentMethodId
            data[PAYMENT_METHOD_TYPE] = paymentMethodType
        }
    }

    private fun validStepError() =
        (errorStep == TrackApiSteps.TOKEN.getType() || errorStep == TrackApiSteps.ASSOCIATION.getType())

    companion object {
        private const val ERROR_STEP = "error_step"
        private const val ERROR_MESSAGE = "error_message"
        private const val BIN = "bin"
        private const val ISSUER = "issuer"
        private const val PAYMENT_METHOD_ID = "payment_method_id"
        private const val PAYMENT_METHOD_TYPE = "payment_type_id"
    }

}

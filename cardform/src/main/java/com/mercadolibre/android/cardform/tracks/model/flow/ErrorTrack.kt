package com.mercadolibre.android.cardform.tracks.model.flow

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

internal class ErrorTrack(private val errorStep: String, private val errorMessage: String): TrackData {
    override val pathEvent = "${BASE_PATH}/error"

    override fun addTrackData(data: MutableMap<String, Any>) {
        data[ERROR_STEP] = errorStep
        data[ERROR_MESSAGE] = errorMessage
    }

    companion object {
        private const val ERROR_STEP = "error_step"
        private const val ERROR_MESSAGE = "error_message"
    }
}
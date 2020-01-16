package com.mercadolibre.android.cardform.tracks.model.identification

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

internal class IdentificationInvalidTrack(private val type: String,
                                 private val value: String): TrackData {
    override val pathEvent = "$BASE_PATH/identification/invalid"

    override fun addTrackData(data: MutableMap<String, Any>) {
        data[IDENTIFICATION_TYPE] = type
        data[IDENTIFICATION_VALUE] = value
    }

    companion object {
        private const val IDENTIFICATION_TYPE = "type"
        private const val IDENTIFICATION_VALUE = "value"
    }
}
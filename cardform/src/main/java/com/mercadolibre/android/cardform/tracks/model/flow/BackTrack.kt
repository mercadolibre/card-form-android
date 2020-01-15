package com.mercadolibre.android.cardform.tracks.model.flow

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

internal class BackTrack(private val stepName: String): TrackData {
    override val pathEvent = "${BASE_PATH}/back"

    override fun addTrackData(data: MutableMap<String, Any>) {
        data[CURRENT_STEP] = stepName
    }
    companion object {
        private const val CURRENT_STEP = "current_step"
    }
}
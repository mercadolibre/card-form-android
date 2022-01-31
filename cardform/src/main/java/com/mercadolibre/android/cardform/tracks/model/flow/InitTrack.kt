package com.mercadolibre.android.cardform.tracks.model.flow

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData
import com.mercadolibre.android.cardform.tracks.model.TrackSteps

internal class InitTrack(private val type: String = TrackSteps.TRADITIONAL.getType()): TrackData {
    override fun addTrackData(data: MutableMap<String, Any>) {
        data[TYPE] = type
    }

    override val pathEvent = "$BASE_PATH/init"
    override val trackGA = false

    companion object {
        private const val TYPE = "type"
    }
}
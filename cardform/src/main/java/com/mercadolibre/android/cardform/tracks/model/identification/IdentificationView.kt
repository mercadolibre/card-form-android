package com.mercadolibre.android.cardform.tracks.model.identification

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

internal class IdentificationView(private val prepopulate: Boolean) : TrackData {
    override val pathEvent = "$BASE_PATH/identification"

    override fun addTrackData(data: MutableMap<String, Any>) {
        data[PREPOPULATE] = prepopulate
    }

    companion object {
        private const val PREPOPULATE = "prepopulated"
    }
}
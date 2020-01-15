package com.mercadolibre.android.cardform.tracks.model.bin

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

internal class BinInvalidTrack(private val bin: String): TrackData {
    override val pathEvent = "$BASE_PATH/bin_number/invalid"
    override fun addTrackData(data: MutableMap<String, Any>) {
        data[BIN_NUMBER] = bin
    }

    companion object {
        private const val BIN_NUMBER = "bin_number"
    }
}
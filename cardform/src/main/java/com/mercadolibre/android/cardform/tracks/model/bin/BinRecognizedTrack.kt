package com.mercadolibre.android.cardform.tracks.model.bin

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class BinRecognizedTrack: Track {
    override val pathEvent = "$BASE_PATH/bin_number/recognized"
    override val trackGA = false
}
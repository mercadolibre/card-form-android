package com.mercadolibre.android.cardform.tracks.model.bin

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class BinValidTrack: Track {
    override val pathEvent = "$BASE_PATH/bin_number/valid"
    override val trackGA = false
}
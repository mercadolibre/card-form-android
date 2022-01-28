package com.mercadolibre.android.cardform.tracks.model.bin

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class BinNumberView : Track {
    override val pathEvent = "$BASE_PATH/bin_number"
    override val trackGA = true
}
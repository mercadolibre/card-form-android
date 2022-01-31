package com.mercadolibre.android.cardform.tracks.model.name

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class NameValidTrack: Track {
    override val pathEvent = "$BASE_PATH/name/valid"
    override val trackGA = false
}
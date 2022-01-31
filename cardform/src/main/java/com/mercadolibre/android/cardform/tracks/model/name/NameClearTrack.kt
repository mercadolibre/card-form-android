package com.mercadolibre.android.cardform.tracks.model.name

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class NameClearTrack: Track {
    override val pathEvent = "$BASE_PATH/name/clear"
    override val trackGA = false
}
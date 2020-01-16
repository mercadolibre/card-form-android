package com.mercadolibre.android.cardform.tracks.model.identification

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class IdentificationValidTrack: Track {
    override val pathEvent = "$BASE_PATH/identification/valid"
}
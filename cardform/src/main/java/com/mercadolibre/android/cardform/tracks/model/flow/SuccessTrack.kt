package com.mercadolibre.android.cardform.tracks.model.flow

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class SuccessTrack: Track {
    override val pathEvent = "$BASE_PATH/success"
}
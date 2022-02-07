package com.mercadolibre.android.cardform.tracks.model.issuers

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class IssuerCloseTrack: Track {
    override val pathEvent = "$BASE_PATH/issuers/close"
    override val trackGA = false
}
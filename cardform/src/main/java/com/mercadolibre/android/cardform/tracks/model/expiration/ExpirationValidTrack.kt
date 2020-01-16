package com.mercadolibre.android.cardform.tracks.model.expiration

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class ExpirationValidTrack: Track {
    override val pathEvent = "$BASE_PATH/expiration_security/date/valid"
}
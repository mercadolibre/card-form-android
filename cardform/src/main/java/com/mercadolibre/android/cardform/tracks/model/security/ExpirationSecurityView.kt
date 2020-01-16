package com.mercadolibre.android.cardform.tracks.model.security

import com.mercadolibre.android.cardform.tracks.Track
import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH

internal class ExpirationSecurityView: Track {
    override val pathEvent = "$BASE_PATH/expiration_security"
}
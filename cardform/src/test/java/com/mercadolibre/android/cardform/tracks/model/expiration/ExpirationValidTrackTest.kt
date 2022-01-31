package com.mercadolibre.android.cardform.tracks.model.expiration

import org.junit.jupiter.api.Test

internal class ExpirationValidTrackTest {

    @Test
    fun validateExpirationValidTrack() {
        val expirationValidTrack = ExpirationValidTrack()
        assert(!expirationValidTrack.trackGA)
        assert("/card_form/expiration_security/date/valid" == expirationValidTrack.pathEvent)
    }

}
package com.mercadolibre.android.cardform.tracks.model.expiration

import org.junit.jupiter.api.Test


internal class ExpirationInvalidTrackTest {

    @Test
    fun validateExpirationInvalidTrack() {
        val expirationInvalidTrack = ExpirationInvalidTrack()
        assert(expirationInvalidTrack.trackGA)
        assert("/card_form/expiration_security/date/invalid" == expirationInvalidTrack.pathEvent)
    }

}
package com.mercadolibre.android.cardform.tracks.model.security

import org.junit.jupiter.api.Test

internal class SecurityValidTrackTest {

    @Test
    fun validateSecurityValidTrack() {
        val securityValidTrack = SecurityValidTrack()
        assert(!securityValidTrack.trackGA)
        assert("/card_form/expiration_security/cvv/valid" == securityValidTrack.pathEvent)
    }

}
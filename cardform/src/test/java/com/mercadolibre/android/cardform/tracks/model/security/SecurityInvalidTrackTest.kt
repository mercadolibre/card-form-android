package com.mercadolibre.android.cardform.tracks.model.security

import org.junit.jupiter.api.Test

internal class SecurityInvalidTrackTest {

    @Test
    fun validateSecurityInvalidTrack() {
        val securityInvalidTrack = SecurityInvalidTrack()
        assert(securityInvalidTrack.trackGA)
        assert("/card_form/expiration_security/cvv/invalid" == securityInvalidTrack.pathEvent)
    }

}
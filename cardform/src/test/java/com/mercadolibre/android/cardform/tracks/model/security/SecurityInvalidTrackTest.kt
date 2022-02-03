package com.mercadolibre.android.cardform.tracks.model.security

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SecurityInvalidTrackTest {

    @Nested
    @DisplayName("Given call event Track Security Invalid")
    inner class GivenCallEventTrackSecurityInvalid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Security Invalid`() {
                val securityInvalidTrack = SecurityInvalidTrack()
                assert(securityInvalidTrack.trackGA)
                assert("/card_form/expiration_security/cvv/invalid" == securityInvalidTrack.pathEvent)
            }
        }
    }

}
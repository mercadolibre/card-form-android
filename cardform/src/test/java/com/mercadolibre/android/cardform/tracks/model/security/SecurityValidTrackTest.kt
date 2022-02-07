package com.mercadolibre.android.cardform.tracks.model.security

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SecurityValidTrackTest {

    @Nested
    @DisplayName("Given call event Track Security Valid")
    inner class GivenCallEventTrackSecurityValid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Security Valid`() {
                val securityValidTrack = SecurityValidTrack()
                assert(!securityValidTrack.trackGA)
                assert("/card_form/expiration_security/cvv/valid" == securityValidTrack.pathEvent)
            }
        }
    }

}
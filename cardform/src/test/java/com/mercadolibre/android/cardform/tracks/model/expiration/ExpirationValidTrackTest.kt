package com.mercadolibre.android.cardform.tracks.model.expiration

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ExpirationValidTrackTest {

    @Nested
    @DisplayName("Given call event Track Expiration Valid")
    inner class GivenCallEventTrackExpirationValid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event ExpirationValid`() {
                val expirationValidTrack = ExpirationValidTrack()
                assert(!expirationValidTrack.trackGA)
                assert("/card_form/expiration_security/date/valid" == expirationValidTrack.pathEvent)
            }

        }
    }
}
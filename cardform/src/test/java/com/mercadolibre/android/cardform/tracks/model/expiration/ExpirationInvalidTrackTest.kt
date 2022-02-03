package com.mercadolibre.android.cardform.tracks.model.expiration

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class ExpirationInvalidTrackTest {

    @Nested
    @DisplayName("Given call event Track Expiration Invalid")
    inner class GivenCallEventTrackExpirationInvalid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event ExpirationInvalid`() {
                val expirationInvalidTrack = ExpirationInvalidTrack()
                assert(expirationInvalidTrack.trackGA)
                assert("/card_form/expiration_security/date/invalid" == expirationInvalidTrack.pathEvent)
            }
        }
    }

}
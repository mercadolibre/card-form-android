package com.mercadolibre.android.cardform.tracks.model.issuers

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class IssuerCloseTrackTest {

    @Nested
    @DisplayName("Given call event Track Issuer Close")
    inner class GivenCallEventTrackIssuerClose {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Issuer Close`() {
                val issuerClose = IssuerCloseTrack()
                assert(!issuerClose.trackGA)
                assert("/card_form/issuers/close" == issuerClose.pathEvent)
            }
        }
    }

}
package com.mercadolibre.android.cardform.tracks.model.issuers

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class IssuerSelectedTrackTest {

    @Nested
    @DisplayName("Given call event Track Issuer Selected")
    inner class GivenCallEventTrackIssuerSelected {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Issuer Selected`() {
                val issuerSelected = IssuerSelectedTrack(12)
                assert(!issuerSelected.trackGA)
                assert("/card_form/issuers/selected" == issuerSelected.pathEvent)
            }
        }
    }

}
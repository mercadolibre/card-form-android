package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class BinUnknownTrackTest {

    @Nested
    @DisplayName("Given call event Track Bin Unknown")
    inner class GivenCallEventTrackBinUnknownTrack {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event bin Unknown`() {
                val binUnknownTrack = BinUnknownTrack("bin_test")
                assert(!binUnknownTrack.trackGA)
                assert("/card_form/bin_number/unknown" == binUnknownTrack.pathEvent)
            }
        }
    }

}
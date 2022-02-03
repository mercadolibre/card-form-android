package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BinClearTrackTest {

    @Nested
    @DisplayName("Given call event Track Bin Clear")
    inner class GivenCallEventTrackBinClear {

        @Nested
        @DisplayName("When event is triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event bin Clear`() {
                val binClearTrack = BinClearTrack()
                assert(!binClearTrack.trackGA)
                assert("/card_form/bin_number/clear" == binClearTrack.pathEvent)
            }
        }
    }

}
package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BinInvalidTrackTest {

    @Nested
    @DisplayName("Given call event Track Bin Invalid")
    inner class GivenCallEventTrackBinInvalid {

        @Nested
        @DisplayName("When event is triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event bin Invalid`() {
                val binInvalidTrack = BinInvalidTrack("bin_test")
                assert(binInvalidTrack.trackGA)
                assert("/card_form/bin_number/invalid" == binInvalidTrack.pathEvent)
            }
        }
    }
}
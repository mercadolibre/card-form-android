package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class NextTrackTest {

    @Nested
    @DisplayName("Given call event Track Next")
    inner class GivenCallEventTrackNext {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event next`() {
                val nextTrack = NextTrack("stepName_test")
                assert(!nextTrack.trackGA)
                assert("/card_form/next" == nextTrack.pathEvent)
            }
        }
    }

}
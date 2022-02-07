package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BackTrackTest {

    @Nested
    @DisplayName("Given call event Track Back")
    inner class GivenCallEventTrackBack {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event back`() {
                val backTrack = BackTrack()
                assert(!backTrack.trackGA)
                assert("/card_form/back" == backTrack.pathEvent)
            }
        }
    }
}
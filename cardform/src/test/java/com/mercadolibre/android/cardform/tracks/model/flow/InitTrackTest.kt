package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class InitTrackTest {

    @Nested
    @DisplayName("Given call event Track Init")
    inner class GivenCallEventTrackInit {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event init`() {
                val initTrack = InitTrack("type_test")
                assert(!initTrack.trackGA)
                assert("/card_form/init" == initTrack.pathEvent)
            }
        }
    }

}
package com.mercadolibre.android.cardform.tracks.model.name

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class NameClearTrackTest {

    @Nested
    @DisplayName("Given call event Track Name Clear")
    inner class GivenCallEventTrackNameClear {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Name Clear`() {
                val nameClear = NameClearTrack()
                assert(!nameClear.trackGA)
                assert("/card_form/name/clear" == nameClear.pathEvent)
            }
        }
    }

}
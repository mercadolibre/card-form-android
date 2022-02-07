package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ErrorTrackTest {

    @Nested
    @DisplayName("Given call event Track Error")
    inner class GivenCallEventTrackError {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event error`() {
                val errorTrack = ErrorTrack("errorStep_test", "errormessage_test")
                assert(!errorTrack.trackGA)
                assert("/card_form/error" == errorTrack.pathEvent)
            }
        }
    }
}
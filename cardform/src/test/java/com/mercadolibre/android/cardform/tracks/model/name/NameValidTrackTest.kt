package com.mercadolibre.android.cardform.tracks.model.name

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class NameValidTrackTest {

    @Nested
    @DisplayName("Given call event Track Name Valid")
    inner class GivenCallEventTrackNameValid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Name Valid`() {
                val nameValid = NameValidTrack()
                assert(!nameValid.trackGA)
                assert("/card_form/name/valid" == nameValid.pathEvent)
            }
        }
    }

}
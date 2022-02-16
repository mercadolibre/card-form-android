package com.mercadolibre.android.cardform.tracks.model.identification

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class IdentificationValidTrackTest {

    @Nested
    @DisplayName("Given call event Track Identification valid")
    inner class GivenCallEventTrackIdentificationValid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Identification valid`() {
                val identificationValid = IdentificationValidTrack()
                assert(!identificationValid.trackGA)
                assert("/card_form/identification/valid" == identificationValid.pathEvent)
            }

        }
    }
}
package com.mercadolibre.android.cardform.tracks.model.identification

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class IdentificationInvalidTrackTest {

    @Nested
    @DisplayName("Given call event Track Identification Invalid")
    inner class GivenCallEventTrackIdentificationInvalid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event Identification Invalid`() {
                val identificationInvalid = IdentificationInvalidTrack("type_test", "value_test")
                assert(identificationInvalid.trackGA)
                assert("/card_form/identification/invalid" == identificationInvalid.pathEvent)
            }
        }
    }

}
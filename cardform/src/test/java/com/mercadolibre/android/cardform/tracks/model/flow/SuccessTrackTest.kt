package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SuccessTrackTest {

    @Nested
    @DisplayName("Given call event Track Success")
    inner class GivenCallEventTrackSuccess {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event success`() {
                val successTrack =
                    SuccessTrack("bin_test", 1, "paymentId_test", "paymentMethod_test")
                assert(!successTrack.trackGA)
                assert("/card_form/success" == successTrack.pathEvent)
            }
        }
    }
}
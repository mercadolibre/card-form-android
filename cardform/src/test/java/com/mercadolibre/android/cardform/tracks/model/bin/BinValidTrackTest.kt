package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BinValidTrackTest {

    @Nested
    @DisplayName("Given call event Track bin valid")
    inner class GivenCallEventTrackBinValid {

        @Nested
        @DisplayName("When event is Triggered")
        inner class WhenEventIsTriggered {

            @Test
            fun `Then track event bin valid`() {
                val binValidTrack =
                    BinValidTrack("mock_bin", 1, "mock_paymentMethodId", "mock_paymentMethodType")
                val map = mutableMapOf<String, Any>()
                val mockMap: MutableMap<String, Any> = mutableMapOf(
                    "bin" to "mock_bin",
                    "issuer" to 1,
                    "payment_method_id" to "mock_paymentMethodId",
                    "payment_method_type_id" to "mock_paymentMethodType"
                )

                binValidTrack.addTrackData(map)

                assert(!binValidTrack.trackGA)
                assert("/card_form/bin_number/valid" == binValidTrack.pathEvent)
                assert(mockMap.size == map.size)
            }
        }
    }
}
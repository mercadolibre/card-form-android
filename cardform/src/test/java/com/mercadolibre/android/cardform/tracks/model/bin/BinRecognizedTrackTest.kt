package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test

internal class BinRecognizedTrackTest {

    @Test
    fun validateBinRecognizedTrack() {
        val binRecognizedTrack = BinRecognizedTrack("mock_bin", 1, "mock_paymentMethodId", "mock_paymentMethodType")
        val map = mutableMapOf<String, Any>()
        val mockMap :MutableMap<String, Any> = mutableMapOf("bin" to  "mock_bin", "issuer" to 1, "payment_method_id" to "mock_paymentMethodId", "payment_method_type_id" to "mock_paymentMethodType")

        binRecognizedTrack.addTrackData(map)

        assert(!binRecognizedTrack.trackGA)
        assert("/card_form/bin_number/recognized" == binRecognizedTrack.pathEvent)
        assert(mockMap.size == map.size)
    }
}
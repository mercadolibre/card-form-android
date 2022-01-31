package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.Test

internal class SuccessTrackTest {

    @Test
    fun validateSuccessTrack() {
        val successTrack = SuccessTrack("bin_test", 1, "paymentId_test", "paymentMethod_test")
        assert(!successTrack.trackGA)
        assert("/card_form/success" == successTrack.pathEvent)
    }
}
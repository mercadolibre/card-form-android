package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test

internal class BinInvalidTrackTest {

    @Test
    fun validateBinInvalidTrack() {
        val binInvalidTrack = BinInvalidTrack("bin_test")
        assert(binInvalidTrack.trackGA)
        assert("/card_form/bin_number/invalid" == binInvalidTrack.pathEvent)
    }
}
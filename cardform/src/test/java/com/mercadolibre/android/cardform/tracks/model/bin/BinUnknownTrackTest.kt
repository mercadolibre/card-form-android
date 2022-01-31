package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test


internal class BinUnknownTrackTest {

    @Test
    fun validateBinUnknownTrack() {
        val binUnknownTrack = BinUnknownTrack("bin_test")
        assert(!binUnknownTrack.trackGA)
        assert("/card_form/bin_number/unknown" == binUnknownTrack.pathEvent)
    }

}
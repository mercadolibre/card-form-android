package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test

internal class BinValidTrackTest {

    @Test
    fun validateBinValidTrack() {
        val binValidTrack = BinValidTrack()
        assert(!binValidTrack.trackGA)
        assert("/card_form/bin_number/valid" == binValidTrack.pathEvent)
    }
}
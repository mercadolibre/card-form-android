package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test

internal class BinClearTrackTest {

    @Test
    fun validateBinClearTrack() {
        val binClearTrack = BinClearTrack()
        assert(!binClearTrack.trackGA)
        assert("/card_form/bin_number/clear" == binClearTrack.pathEvent)
    }

}
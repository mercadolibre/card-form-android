package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test

internal class BinRecognizedTrackTest {

    @Test
    fun validateBinRecognizedTrack() {
        val binRecognizedTrack = BinRecognizedTrack()
        assert(!binRecognizedTrack.trackGA)
        assert("/card_form/bin_number/recognized" == binRecognizedTrack.pathEvent)
    }
}
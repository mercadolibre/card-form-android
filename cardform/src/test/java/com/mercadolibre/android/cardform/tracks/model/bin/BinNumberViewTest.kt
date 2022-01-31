package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.Test


internal class BinNumberViewTest {

    @Test
    fun validateBinNumberTrack() {
        val binNumberView = BinNumberView()
        assert(binNumberView.trackGA)
        assert("/card_form/bin_number" == binNumberView.pathEvent)
    }
}
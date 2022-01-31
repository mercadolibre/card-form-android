package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.Test

internal class NextTrackTest {

    @Test
    fun validateNextTrack() {
        val nextTrack = NextTrack("stepName_test")
        assert(!nextTrack.trackGA)
        assert("/card_form/next" == nextTrack.pathEvent)
    }

}
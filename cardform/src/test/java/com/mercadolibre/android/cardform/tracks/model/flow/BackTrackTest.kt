package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.Test

internal class BackTrackTest {

    @Test
    fun validateBackTrack() {
        val backTrack = BackTrack()
        assert(!backTrack.trackGA)
        assert("/card_form/back" == backTrack.pathEvent)
    }
}
package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.Test

internal class InitTrackTest {

    @Test
    fun validateInitTrack() {
        val initTrack = InitTrack("type_test")
        assert(!initTrack.trackGA)
        assert("/card_form/init" == initTrack.pathEvent)
    }

}
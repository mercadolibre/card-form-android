package com.mercadolibre.android.cardform.tracks.model.name

import org.junit.jupiter.api.Test


internal class NameClearTrackTest {

    @Test
    fun validateNameClearTrack() {
        val nameClear = NameClearTrack()
        assert(!nameClear.trackGA)
        assert("/card_form/name/clear" == nameClear.pathEvent)
    }

}
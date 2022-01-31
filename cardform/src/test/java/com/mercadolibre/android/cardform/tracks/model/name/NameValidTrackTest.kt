package com.mercadolibre.android.cardform.tracks.model.name

import org.junit.jupiter.api.Test

internal class NameValidTrackTest {

    @Test
    fun validateNameValidTrack() {
        val nameValid = NameValidTrack()
        assert(!nameValid.trackGA)
        assert("/card_form/name/valid" == nameValid.pathEvent)
    }

}
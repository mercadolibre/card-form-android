package com.mercadolibre.android.cardform.tracks.model.identification

import org.junit.jupiter.api.Test

internal class IdentificationValidTrackTest {

    @Test
    fun validateIdentificationValidTrack() {
        val identificationValid = IdentificationValidTrack()
        assert(!identificationValid.trackGA)
        assert("/card_form/identification/valid" == identificationValid.pathEvent)
    }

}
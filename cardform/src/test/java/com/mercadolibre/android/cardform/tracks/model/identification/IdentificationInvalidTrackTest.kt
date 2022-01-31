package com.mercadolibre.android.cardform.tracks.model.identification

import org.junit.jupiter.api.Test

internal class IdentificationInvalidTrackTest {

    @Test
    fun validateIdentificationInvalidTrack() {
        val identificationInvalid = IdentificationInvalidTrack("type_test", "value_test")
        assert(identificationInvalid.trackGA)
        assert("/card_form/identification/invalid" == identificationInvalid.pathEvent)
    }

}
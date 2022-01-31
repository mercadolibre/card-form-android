package com.mercadolibre.android.cardform.tracks.model.identification

import org.junit.jupiter.api.Test

internal class IdentificationViewTest {

    @Test
    fun validateIdentificationViewTrack() {
        val identificationView = IdentificationView(false)
        assert(identificationView.trackGA)
        assert("/card_form/identification" == identificationView.pathEvent)
    }

}
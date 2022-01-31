package com.mercadolibre.android.cardform.tracks.model.issuers

import org.junit.jupiter.api.Test

internal class IssuerSelectedTrackTest {

    @Test
    fun validateIssuerSelectedTrack() {
        val issuerSelected = IssuerSelectedTrack(12)
        assert(!issuerSelected.trackGA)
        assert("/card_form/issuers/selected" == issuerSelected.pathEvent)
    }

}
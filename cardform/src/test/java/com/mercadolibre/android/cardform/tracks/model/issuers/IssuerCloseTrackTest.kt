package com.mercadolibre.android.cardform.tracks.model.issuers

import org.junit.jupiter.api.Test

internal class IssuerCloseTrackTest {

    @Test
    fun validateIssuerCloseTrack() {
        val issuerClose = IssuerCloseTrack()
        assert(!issuerClose.trackGA)
        assert("/card_form/issuers/close" == issuerClose.pathEvent)
    }

}
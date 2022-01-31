package com.mercadolibre.android.cardform.tracks.model.issuers

import org.junit.jupiter.api.Test

internal class IssuersViewTest {

    @Test
    fun validateIssuerViewTrack() {
        val issuerView = IssuersView(12)
        assert(issuerView.trackGA)
        assert("/card_form/issuers" == issuerView.pathEvent)
    }

}
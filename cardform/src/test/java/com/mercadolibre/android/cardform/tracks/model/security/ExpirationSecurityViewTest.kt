package com.mercadolibre.android.cardform.tracks.model.security

import org.junit.jupiter.api.Test

internal class ExpirationSecurityViewTest {

    @Test
    fun validateExpirationSecurityViewTrack() {
        val expirationSecurityView = ExpirationSecurityView()
        assert(expirationSecurityView.trackGA)
        assert("/card_form/expiration_security" == expirationSecurityView.pathEvent)
    }

}
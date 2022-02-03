package com.mercadolibre.android.cardform.tracks.model.security

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ExpirationSecurityViewTest {

    @Nested
    @DisplayName("Given open view expiration security")
    inner class GivenOpenViewExpirationSecurity {

        @Nested
        @DisplayName("When enter to this view")
        inner class WhenEnterToThisView {

            @Test
            fun `Then track view expiration security`() {
                val expirationSecurityView = ExpirationSecurityView()
                assert(expirationSecurityView.trackGA)
                assert("/card_form/expiration_security" == expirationSecurityView.pathEvent)
            }
        }
    }

}
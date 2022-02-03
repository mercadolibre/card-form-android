package com.mercadolibre.android.cardform.tracks.model.issuers

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class IssuersViewTest {

    @Nested
    @DisplayName("Given open view issuers")
    inner class GivenOpenViewIdentification {

        @Nested
        @DisplayName("When enter to this view")
        inner class WhenEnterToThisView {

            @Test
            fun `Then track view issuers`() {
                val issuerView = IssuersView(12)
                assert(issuerView.trackGA)
                assert("/card_form/issuers" == issuerView.pathEvent)
            }
        }
    }
}
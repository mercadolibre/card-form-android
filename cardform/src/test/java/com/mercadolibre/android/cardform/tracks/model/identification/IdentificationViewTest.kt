package com.mercadolibre.android.cardform.tracks.model.identification

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class IdentificationViewTest {

    @Nested
    @DisplayName("Given open view identification")
    inner class GivenOpenViewIdentification {

        @Nested
        @DisplayName("When enter to this view")
        inner class WhenEnterToThisView {

            @Test
            fun `Then track view identification`() {
                val identificationView = IdentificationView(false)
                assert(identificationView.trackGA)
                assert("/card_form/identification" == identificationView.pathEvent)
            }
        }
    }

}
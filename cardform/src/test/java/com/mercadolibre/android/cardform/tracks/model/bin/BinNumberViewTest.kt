package com.mercadolibre.android.cardform.tracks.model.bin

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class BinNumberViewTest {

    @Nested
    @DisplayName("Given open view Bin Number card")
    inner class GivenOpenViewBinNumber {

        @Nested
        @DisplayName("When enter to this view")
        inner class WhenEnterToThisView {

            @Test
            fun `Then track view bin Number`() {
                val binNumberView = BinNumberView()
                assert(binNumberView.trackGA)
                assert("/card_form/bin_number" == binNumberView.pathEvent)
            }
        }
    }
}
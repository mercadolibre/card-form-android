package com.mercadolibre.android.cardform.tracks.model.webview

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class WebViewTrackTest {

    @Nested
    @DisplayName("Given open webview")
    inner class GivenOpenViewExpirationSecurity {

        @Nested
        @DisplayName("When enter to this view")
        inner class WhenEnterToThisView {

            @Test
            fun `Then track webview`() {
                val webViewTrack = WebViewTrack("url_test")
                assert(!webViewTrack.trackGA)
                assert("/card_form/web_view" == webViewTrack.pathEvent)
            }
        }
    }

}
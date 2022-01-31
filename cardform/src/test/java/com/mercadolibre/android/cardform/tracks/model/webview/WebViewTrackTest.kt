package com.mercadolibre.android.cardform.tracks.model.webview

import org.junit.jupiter.api.Test

internal class WebViewTrackTest {

    @Test
    fun validateWebViewTrack() {
        val webViewTrack = WebViewTrack("url_test")
        assert(!webViewTrack.trackGA)
        assert("/card_form/web_view" == webViewTrack.pathEvent)
    }

}
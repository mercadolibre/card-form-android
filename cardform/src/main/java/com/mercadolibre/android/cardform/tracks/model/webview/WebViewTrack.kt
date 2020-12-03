package com.mercadolibre.android.cardform.tracks.model.webview

import com.mercadolibre.android.cardform.tracks.TrackData

internal class WebViewTrack(private val url: String): TrackData {
    override fun addTrackData(data: MutableMap<String, Any>) {
        data[URL] = url
    }

    override val pathEvent = "/card_form/web_view"

    companion object {
        private const val URL = "url"
    }
}
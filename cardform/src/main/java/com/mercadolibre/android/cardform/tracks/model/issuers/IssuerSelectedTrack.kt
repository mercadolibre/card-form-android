package com.mercadolibre.android.cardform.tracks.model.issuers

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

class IssuerSelectedTrack(private val issuerId: Int): TrackData {
    override val pathEvent = "$BASE_PATH/issuers/selected"
    override fun addTrackData(data: MutableMap<String, Any>) {
        data[ISSUER_ID] = issuerId
    }

    companion object {
        private const val ISSUER_ID = "issuer_id"
    }
}
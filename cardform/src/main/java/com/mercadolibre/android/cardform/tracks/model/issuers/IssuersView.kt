package com.mercadolibre.android.cardform.tracks.model.issuers

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

class IssuersView(private val issuersQuantity: Int) : TrackData {
    override val pathEvent = "$BASE_PATH/issuers"

    override fun addTrackData(data: MutableMap<String, Any>) {
        data[ISSUERS_QUANTITY] = issuersQuantity
    }

    companion object {
        private const val ISSUERS_QUANTITY = "issuers_quantity"
    }
}
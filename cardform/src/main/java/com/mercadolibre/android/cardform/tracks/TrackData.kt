package com.mercadolibre.android.cardform.tracks

internal interface TrackData: Track {
    fun addTrackData(data: MutableMap<String, Any>)
}
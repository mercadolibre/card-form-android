package com.mercadolibre.android.cardform.tracks

internal interface Tracker {
    fun trackView(track: Track) = Unit
    fun trackEvent(track: Track) = Unit
}
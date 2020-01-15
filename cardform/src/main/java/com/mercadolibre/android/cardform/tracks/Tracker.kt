package com.mercadolibre.android.cardform.tracks

interface Tracker {
    fun trackView(track: Track) = Unit
    fun trackEvent(track: Track) = Unit
}
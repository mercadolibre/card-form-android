package com.mercadolibre.android.cardform.tracks

internal interface Track {
    val pathEvent: String
    companion object{
        const val BASE_PATH = "/card_form"
    }
}
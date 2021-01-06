package com.mercadolibre.android.cardform.tracks.model

internal enum class TrackApiSteps(private val type: String) {
    BIN_NUMBER("bin_number"),
    TOKEN("save_card_token"),
    ASSOCIATION("save_card_data"),
    INIT_INSCRIPTION("init_inscription"),
    FINISH_INSCRIPTION("finish_inscription");

    fun getType() = type
}
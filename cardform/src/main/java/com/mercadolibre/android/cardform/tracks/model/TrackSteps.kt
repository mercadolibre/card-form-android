package com.mercadolibre.android.cardform.tracks.model

internal enum class TrackSteps(private val type: String) {
    BIN_NUMBER("bin_number"),
    NAME("name"),
    EXPIRATION("expiration_security_date"),
    SECURITY("expiration_security_cvv"),
    IDENTIFICATION("identification"),
    TRADITIONAL("traditional"),
    WEB_VIEW("web_view");

    fun getType() = type
}
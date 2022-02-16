package com.mercadolibre.android.cardform.tracks.model.bin

import com.mercadolibre.android.cardform.tracks.Track.Companion.BASE_PATH
import com.mercadolibre.android.cardform.tracks.TrackData

internal class BinValidTrack(
    private val bin: String,
    private val issuer: Int,
    private val paymentMethodId: String,
    private val paymentMethodType: String
): TrackData {
    override fun addTrackData(data: MutableMap<String, Any>) {
        data[BIN]  = bin
        data[ISSUER] = issuer
        data[PAYMENT_METHOD_ID] = paymentMethodId
        data[PAYMENT_METHOD_TYPE] = paymentMethodType
    }

    override val pathEvent = "$BASE_PATH/bin_number/valid"
    override val trackGA = false

    companion object {
        private const val BIN = "bin"
        private const val ISSUER = "issuer"
        private const val PAYMENT_METHOD_ID = "payment_method_id"
        private const val PAYMENT_METHOD_TYPE = "payment_method_type"
    }
}

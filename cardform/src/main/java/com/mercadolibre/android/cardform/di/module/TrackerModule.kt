package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.tracks.CardFormTracker
import com.mercadolibre.android.cardform.tracks.TrackerData
import com.mercadopago.android.px.addons.TrackingBehaviour

class TrackerModule(
    private val siteId: String,
    private val flowId: String,
    private val sessionId: String,
    private val trackingBehaviour: TrackingBehaviour
) {

    val tracker by lazy {
        CardFormTracker(
            TrackerData(
                siteId,
                flowId,
                sessionId
            ),
            trackingBehaviour
        )
    }
}
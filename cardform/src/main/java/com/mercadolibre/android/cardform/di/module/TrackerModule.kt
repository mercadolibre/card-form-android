package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.tracks.CardFormTracker
import com.mercadolibre.android.cardform.tracks.TrackerData
import com.mercadopago.android.px.addons.TrackingBehaviour

class TrackerModule(
    siteId: String,
    flowId: String,
    sessionId: String,
    trackingBehaviour: TrackingBehaviour
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
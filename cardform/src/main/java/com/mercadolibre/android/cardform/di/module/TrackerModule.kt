package com.mercadolibre.android.cardform.di.module

import com.mercadolibre.android.cardform.tracks.CardFormTracker
import com.mercadolibre.android.cardform.tracks.TrackerData

class TrackerModule(
    private val siteId: String,
    private val flowId: String,
    private val sessionId: String
) {

    val tracker by lazy {
        CardFormTracker(
            TrackerData(
                siteId,
                flowId,
                sessionId
            )
        )
    }
}
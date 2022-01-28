package com.mercadolibre.android.cardform.tracks

import android.util.Log
import com.mercadolibre.android.cardform.BuildConfig
import com.mercadopago.android.px.addons.TrackingBehaviour
import java.util.*
import kotlin.collections.HashMap

internal class CardFormTracker(baseData: TrackerData, private val behaviour: TrackingBehaviour) :
    Tracker {

    private val dataMap: HashMap<String, Any>

    init {
        dataMap = hashMapOf(
            SITE_ID to baseData.siteId,
            FLOW_ID to baseData.flowId,
            SESSION_ID to baseData.sessionId
        )
    }

    private fun addDataTrack(track: Track, trackerListener: (MutableMap<String, Any>) -> Unit) {
        val data = dataMap.toMutableMap()
        data[SESSION_TIME] = Calendar.getInstance().time.time
        if (track is TrackData) {
            track.addTrackData(data)
        }
        trackerListener(data)
    }

    private fun trackMelidataGA(track: Track,
                                trackerMap: MutableMap<String, Any>,
                                type: com.mercadopago.android.px.addons.model.Track.Type
    ) {
        val trackers: List<com.mercadopago.android.px.addons.tracking.Tracker> = if (track.trackGA)
            listOf(com.mercadopago.android.px.addons.tracking.Tracker.GOOGLE_ANALYTICS_V2,
                com.mercadopago.android.px.addons.tracking.Tracker.CUSTOM)
        else listOf(com.mercadopago.android.px.addons.tracking.Tracker.CUSTOM)

        val myTrackView = com.mercadopago.android.px.addons.model.Track.Builder(
            com.mercadopago.android.px.addons.tracking.Tracker.MELIDATA,
            "CARD_FORM",
            type, track.pathEvent)
            .addTrackers(trackers)
            .addData(trackerMap)
            .build()

        behaviour.track(myTrackView)
    }

    override fun trackView(track: Track) {
        addDataTrack(track) {
            logDebug(track.pathEvent, it.toString())
            trackMelidataGA(track, it, com.mercadopago.android.px.addons.model.Track.Type.VIEW)
        }
    }

    override fun trackEvent(track: Track) {
        addDataTrack(track) {
            logDebug(track.pathEvent, it.toString())
            trackMelidataGA(track, it, com.mercadopago.android.px.addons.model.Track.Type.EVENT)
        }
    }

    private fun logDebug(path: String, data: String) {
        if (BuildConfig.DEBUG) {
            Log.d(path, data)
        }
    }

    companion object {
        private const val SITE_ID = "site_id"
        private const val FLOW_ID = "flow_id"
        private const val SESSION_ID = "session_id"
        private const val SESSION_TIME = "session_time"
    }
}
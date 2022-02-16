package com.mercadolibre.android.cardform.tracks

import android.util.Log
import com.mercadolibre.android.cardform.BuildConfig
import com.mercadopago.android.px.addons.TrackingBehaviour
import java.util.*
import kotlin.collections.HashMap
import com.mercadopago.android.px.addons.model.Track as PXTrack
import com.mercadopago.android.px.addons.tracking.Tracker as PXTracker

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

    private fun track(
        track: Track,
        trackerMap: MutableMap<String, Any>,
        type: PXTrack.Type
    ) {
        val trackers: List<PXTracker> = if (track.trackGA) {
            listOf(PXTracker.GOOGLE_ANALYTICS_V2, PXTracker.CUSTOM)
        } else {
            listOf(PXTracker.CUSTOM)
        }

        val pxTrack = PXTrack.Builder(
            PXTracker.MELIDATA,
            APPLICATION_CONTEXT,
            type, track.pathEvent)
            .addTrackers(trackers)
            .addData(trackerMap)
            .build()

        behaviour.track(pxTrack)
    }

    override fun trackView(track: Track) {
        addDataTrack(track) {
            logDebug(track.pathEvent, it.toString())
            track(track, it, PXTrack.Type.VIEW)
        }
    }

    override fun trackEvent(track: Track) {
        addDataTrack(track) {
            logDebug(track.pathEvent, it.toString())
            track(track, it, PXTrack.Type.EVENT)
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
        private const val APPLICATION_CONTEXT = "CARD_FORM"
    }
}
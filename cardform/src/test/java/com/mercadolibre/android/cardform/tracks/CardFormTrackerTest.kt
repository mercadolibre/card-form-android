package com.mercadolibre.android.cardform.tracks

import com.mercadolibre.android.cardform.di.module.TrackerModule
import com.mercadolibre.android.cardform.tracks.model.bin.BinClearTrack
import com.mercadolibre.android.cardform.tracks.model.bin.BinInvalidTrack
import com.mercadopago.android.px.addons.TrackingBehaviour
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CardFormTrackerTest {

    private lateinit var cardFormTracker: CardFormTracker
    private lateinit var trackingBehaviour: TrackingBehaviour
    private lateinit var trackerModule: TrackerModule
    private lateinit var track: BinInvalidTrack

    @BeforeEach
    fun doBefore() {
        trackingBehaviour = mockk<TrackingBehaviour>(relaxed = true)
        track = BinInvalidTrack("bintest")

        val trackerData = TrackerData(
            "siteIdMock",
            "flowIdMock",
            "sessionIdMock"
        )

        cardFormTracker = CardFormTracker(trackerData, trackingBehaviour)
        trackerModule = TrackerModule("siteIdMock",
            "flowIdMock",
            "sessionIdMock",
            trackingBehaviour
        )

    }

    @Test
    fun trackEvent() {

        cardFormTracker.trackEvent(track)
        verify {
            trackingBehaviour.track(any())
        }
    }

    @Test
    fun trackView() {
        cardFormTracker.trackView(track)
        verify {
            trackingBehaviour.track(any())
        }
    }

    @Test
    fun trackViewNoGa() {
        val trackTest = BinClearTrack()
        cardFormTracker.trackView(trackTest)

        verify {
            trackingBehaviour.track(any())
        }
    }

}
package com.mercadolibre.android.cardform.tracks

import com.mercadolibre.android.cardform.di.module.TrackerModule
import com.mercadolibre.android.cardform.tracks.model.bin.BinClearTrack
import com.mercadolibre.android.cardform.tracks.model.bin.BinInvalidTrack
import com.mercadopago.android.px.addons.TrackingBehaviour
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class CardFormTrackerTest {

    @Nested
    @DisplayName("Given Initialized Tracker")
    internal inner class GivenInitializedTracker {

        @Nested
        @DisplayName("When user clicked an option track event or enter a view")
        inner class WhenUserClickedAnOptionOrEnterView {

            private lateinit var cardFormTracker: CardFormTracker
            private lateinit var trackingBehaviour: TrackingBehaviour
            private lateinit var trackerModule: TrackerModule
            private lateinit var track: BinInvalidTrack

            @BeforeEach
            fun setUp() {
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
            fun `Then Track event on analytics and melidata`() {
                cardFormTracker.trackEvent(track)
                verify {
                    trackingBehaviour.track(any())
                }
            }

            @Test
            fun `Then Track view on analytics and melidata`() {
                cardFormTracker.trackView(track)
                verify {
                    trackingBehaviour.track(any())
                }
            }

            @Test
            fun `Then Track view on melidata`() {
                val trackTest = BinClearTrack()
                cardFormTracker.trackView(trackTest)

                verify {
                    trackingBehaviour.track(any())
                }
            }
        }
    }
}
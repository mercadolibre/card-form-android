package com.mercadolibre.android.cardform.tracks.model.flow

import org.junit.jupiter.api.Test

internal class ErrorTrackTest {

    @Test
    fun validateBackTrack() {
        val errorTrack = ErrorTrack("errorStep_test", "errormessage_test")
        assert(!errorTrack.trackGA)
        assert("/card_form/error" == errorTrack.pathEvent)
    }

}
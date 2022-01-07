package com.mercadolibre.android.cardform.data.mapper

import com.mercadolibre.android.cardform.data.model.body.FinishInscriptionBody
import com.mercadolibre.android.cardform.domain.FinishInscriptionParam
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class FinishInscriptionBodyMapperTest {

    @Nested
    @DisplayName("Given a finish conversion is requested inscription body")
    inner class GivenFinishConversionIsRequestedInscriptionBody {

        @Nested
        @DisplayName("")
        inner class When {

            private lateinit var finishInscriptionBody: FinishInscriptionBody
            private val finishInscriptionParam = FinishInscriptionParam(
                "12345678", "user name",
                "1234123412341234", "visa-credit"
            )
            private val subject = FinishInscriptionBodyMapper("MLA")

            @Test
            fun map() {
                finishInscriptionBody = subject.map(finishInscriptionParam)
                assertEquals(finishInscriptionParam.identificationNumber, finishInscriptionBody.cardHolder.identification.number)
            }
        }
    }
}
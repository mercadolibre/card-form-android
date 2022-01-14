package com.mercadolibre.android.cardform.data.mapper

import com.mercadolibre.android.cardform.domain.FinishInscriptionParam
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class FinishInscriptionBodyMapperTest {

    @Nested
    @DisplayName("Given a finish conversion is requested inscription body")
    inner class GivenFinishConversionIsRequestedInscriptionBody {

        @Nested
        @DisplayName("When requested a conversion")
        inner class WhenRequestedConversion {


            private val expectedSite = "MLA"
            private val expected = FinishInscriptionParam(
                "12345678", "user name",
                "1234123412341234", "visa-credit"
            )
            private val subject = FinishInscriptionBodyMapper(expectedSite)
            private val finishInscriptionBody = subject.map(expected)

            @Test
            fun `FinishInscriptionParam to mapper FinishInscriptionBody verify site`() {
                assertEquals(expectedSite, finishInscriptionBody.siteId)
            }

            @Test
            fun `FinishInscriptionParam to mapper FinishInscriptionBody verify tbkToken`() {
                assertEquals(expected.tbkToken, finishInscriptionBody.tbkToken)
            }

            @Test
            fun `FinishInscriptionParam to mapper FinishInscriptionBody verify identification number`() {
                assertEquals(
                    expected.identificationNumber,
                    finishInscriptionBody.cardHolder.identification.number
                )
            }

            @Test
            fun `FinishInscriptionParam to mapper FinishInscriptionBody verify identification type`() {
                assertEquals(
                    expected.identificationType,
                    finishInscriptionBody.cardHolder.identification.type
                )
            }

            @Test
            fun `FinishInscriptionParam to mapper FinishInscriptionBody verify name`() {
                assertEquals(expected.userName, finishInscriptionBody.cardHolder.name)
            }
        }
    }
}
package com.mercadolibre.android.cardform.data.mapper

import com.mercadolibre.android.cardform.data.model.request.FinishInscriptionParam
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class FinishInscriptionBodyMapperTest {

    @Nested
    @DisplayName("Given that a map is requested for FinishInscriptionBody")
    inner class GivenThatAMapIsRequestedForFinishInscriptionBody {

        @Nested
        @DisplayName("When prompted for a conversion from FinishInscriptionParam to FinishInscriptionBody with success")
        inner class WhenPromptedForAConversionFromFinishInscriptionParamToFinishInscriptionBodyWithSuccess {

            private val expectedSite = "MLA"
            private val expected = FinishInscriptionParam(
                "12345678", "user name",
                "1234123412341234", "visa-credit"
            )
            private val subject = FinishInscriptionBodyMapper(expectedSite)
            private val finishInscriptionBody = subject.map(expected)

            @Test
            fun `Then check the site field received the correct value`() {
                assertEquals(expectedSite, finishInscriptionBody.siteId)
            }

            @Test
            fun `Then check the tbkToken field received the correct value`() {
                assertEquals(expected.tbkToken, finishInscriptionBody.tbkToken)
            }

            @Test
            fun `Then check the number field received the correct value`() {
                assertEquals(
                    expected.identificationNumber,
                    finishInscriptionBody.cardHolder.identification.number
                )
            }

            @Test
            fun `Then check the type field received the correct value`() {
                assertEquals(
                    expected.identificationType,
                    finishInscriptionBody.cardHolder.identification.type
                )
            }

            @Test
            fun `Then check the name field received the correct valuee`() {
                assertEquals(expected.userName, finishInscriptionBody.cardHolder.name)
            }
        }
    }
}
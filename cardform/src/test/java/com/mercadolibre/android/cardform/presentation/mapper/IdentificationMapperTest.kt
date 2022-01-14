package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import com.mercadolibre.android.cardform.data.model.response.IdentificationTypes
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class IdentificationMapperTest {

    @Nested
    @DisplayName("Given a List<IdentificationTypes> conversion is requested IdentificationData")
    inner class GivenCardStepConversionIsRequestedCardBody {

        @Nested
        @DisplayName("When requested a conversion")
        inner class WhenRequestedConversion {

            private val fieldsSetting = mockk<FieldsSetting>(relaxed = true)
            private val subject = IdentificationMapper(fieldsSetting)
            private val expected = listOf(
                IdentificationTypes(
                    "01",
                    "name",
                    "credit",
                    "##.##",
                    5, 10
                )
            )
            private val identificationData = subject.map(expected)

            @Test
            fun `List of IdentificationTypes to mapper IdentificationData verify identification id`() {
                assertEquals(expected[0].id, identificationData.identifications[0].id)
            }

            @Test
            fun `List of IdentificationTypes to mapper IdentificationData verify identification name`() {
                assertEquals(expected[0].name, identificationData.identifications[0].name)
            }

            @Test
            fun `List of IdentificationTypes to mapper IdentificationData verify identification type`() {
                assertEquals(expected[0].type, identificationData.identifications[0].type)
            }

            @Test
            fun `List of IdentificationTypes to mapper IdentificationData verify identification mask`() {
                assertEquals(expected[0].mask, identificationData.identifications[0].mask)
            }

            @Test
            fun `List of IdentificationTypes to mapper IdentificationData verify identification min length`() {
                assertEquals(expected[0].minLength, identificationData.identifications[0].minLength)
            }

            @Test
            fun `List of IdentificationTypes to mapper IdentificationData verify identification max length`() {
                assertEquals(expected[0].maxLength, identificationData.identifications[0].maxLength)
            }
        }
    }
}
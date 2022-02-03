package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.FieldsSettingProvider
import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import com.mercadolibre.android.cardform.data.model.response.IdentificationTypes
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class IdentificationMapperTest {

    @Nested
    @DisplayName("Given a conversion of the Identification Types, the Identification Data is requested")
    inner class GivenAConversionOfTheIdentificationTypesTheIdentificationDataIsRequested {

        @Nested
        @DisplayName("When prompted for a conversion from list IdentificationTypes to identificationData with success")
        inner class WhenPromptedForAConversionFromListIdentificationTypesToIdentificationDataWithSuccess {

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
            fun `Then check the id field received the correct value`() {
                assertEquals(expected[0].id, identificationData.identifications[0].id)
            }

            @Test
            fun `Then check the name field received the correct value`() {
                assertEquals(expected[0].name, identificationData.identifications[0].name)
            }

            @Test
            fun `Then check the type field received the correct value`() {
                assertEquals(expected[0].type, identificationData.identifications[0].type)
            }

            @Test
            fun `Then check the mask field received the correct value`() {
                assertEquals(expected[0].mask, identificationData.identifications[0].mask)
            }

            @Test
            fun `Then check the minLength field received the correct value`() {
                assertEquals(expected[0].minLength, identificationData.identifications[0].minLength)
            }

            @Test
            fun `Then check the maxLength field received the correct value`() {
                assertEquals(expected[0].maxLength, identificationData.identifications[0].maxLength)
            }
        }

        @Nested
        @DisplayName("When requested a FieldsSetting to IdentificationData conversion with null autocomplete")
        inner class WhenRequestedAFieldSettingToIdentificationDataConversionWithNullAutocomplete {

            private val fieldsSetting = FieldsSettingProvider.makeFieldSetting(null)
            private val subject = IdentificationMapper(fieldsSetting)

            private val stepData = subject.map(mockk(relaxed = true))

            @Test
            fun `Then check the step data autocomplete field defaults to true`() {
                assertEquals(true, stepData.autocomplete)
            }
        }
    }
}

package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class InputMapperTest {

    @Nested
    @DisplayName("Given a request to convert from FieldsSetting to StepData")
    inner class GivenARequestToConvertFromFieldsSettingToStepData {

        @Nested
        @DisplayName("When requested a successful FieldsSetting to stepData conversion")
        inner class WhenRequestedASuccessfulFieldsSettingToStepDataConversion {

            private val subject = InputMapper
            private val expected = FieldsSetting(
                "name",
                40,
                "text",
                "Card holder's name, hintMessage=As it shows on the card",
                "",
                "^[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]+(([',. -][a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ ])?[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]*)*$",
                "Complete using only letters",
                ""
            )

            val stepData = subject.map(expected)

            @Test
            fun `Then check the name field received the correct value`() {
                assertEquals(expected.name, stepData.name)
            }

            @Test
            fun `Then check the maxLength field received the correct value`() {
                assertEquals(expected.length, stepData.maxLength)
            }

            @Test
            fun `Then check the type field received the correct value`() {
                assertEquals(expected.type, stepData.type)
            }

            @Test
            fun `Then check the title field received the correct value`() {
                assertEquals(expected.title, stepData.title)
            }

            @Test
            fun `Then check the hintMessage field received the correct value`() {
                assertEquals(expected.hintMessage, stepData.hintMessage)
            }

            @Test
            fun `Then check the validationPattern field received the correct value`() {
                assertEquals(expected.validationPattern, stepData.validationPattern)
            }

            @Test
            fun `Then check the validationMessage field received the correct value`() {
                assertEquals(expected.validationMessage, stepData.validationMessage)
            }

            @Test
            fun `Then check the mask field received the correct value`() {
                assertEquals(expected.mask, stepData.mask)
            }
        }
    }
}
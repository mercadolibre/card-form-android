package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class InputMapperTest {

    @Nested
    @DisplayName("Given a Fields Setting conversion is requested Step Data")
    inner class GivenFieldsSettingConversionIsRequestedStepData {

        @Nested
        @DisplayName("When requested a conversion")
        inner class WhenRequestedConversion {

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
            fun `FieldsSetting to mapper StepData verify name`() {
                assertEquals(expected.name, stepData.name)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify maxLength`() {
                assertEquals(expected.length, stepData.maxLength)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify type`() {
                assertEquals(expected.type, stepData.type)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify title`() {
                assertEquals(expected.title, stepData.title)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify hintMessage`() {
                assertEquals(expected.hintMessage, stepData.hintMessage)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify validationPattern`() {
                assertEquals(expected.validationPattern, stepData.validationPattern)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify validationMessage`() {
                assertEquals(expected.validationMessage, stepData.validationMessage)
            }

            @Test
            fun `FieldsSetting to mapper StepData verify mask`() {
                assertEquals(expected.mask, stepData.mask)
            }
        }
    }
}
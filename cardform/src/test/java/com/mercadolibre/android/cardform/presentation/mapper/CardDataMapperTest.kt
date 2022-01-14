package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.data.model.response.CardUi
import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import com.mercadolibre.android.cardform.data.model.response.IdentificationTypes
import com.mercadolibre.android.cardform.data.model.response.Issuer
import com.mercadolibre.android.cardform.data.model.response.OtherTexts
import com.mercadolibre.android.cardform.data.model.response.PaymentMethod
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

internal class CardDataMapperTest {

    @Nested
    @DisplayName("Given a register card conversion is requested card data")
    inner class GivenCardStepConversionIsRequestedCardBody {

        @Nested
        @DisplayName("When requested a conversion")
        inner class WhenRequestedConversion {

            private val subject = CardDataMapper
            private val expected = RegisterCard(
                OtherTexts("New credit card"),
                true,
                PaymentMethod("credit_card", "visa", "visa", listOf("aggregator")),
                CardUi(
                    16,
                    "#2561C0",
                    "#F5F5F5",
                    "light",
                    "back",
                    3,
                    "https://mobile.mercadolibre.com/remote_resources/image/card_drawer_mla_pm_visa_white?density=xhdpi&locale=en",
                    "",
                    IntArray(4) { 4 * (it) },
                    "standard",
                    ArrayList()
                ),
                listOf("name", "identification_types"),
                listOf(Issuer("visa", 1, null, false)),
                listOf(
                    FieldsSetting(
                        "name",
                        40,
                        "text",
                        "Card holder's name, hintMessage=As it shows on the card",
                        "",
                        "^[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]+(([',. -][a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ ])?[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]*)*$",
                        "Complete using only letters",
                        ""
                    )
                ),
                listOf(IdentificationTypes("DNI", "DNI", "number", "$$.$$$.$$$", 7, 8))
            )
            private val cardData = subject.map(expected)

            @Test
            fun `RegisterCard to mapper CardData verify cardFormTitle`() {
                assertEquals(expected.otherTexts.cardFormTitle, cardData.formTitle)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi cardNumberLength`() {
                assertEquals(expected.cardUi.cardNumberLength, cardData.cardUi!!.cardNumberLength)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi cardColor`() {
                assertEquals(expected.cardUi.cardColor, cardData.cardUi!!.cardColor)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi cardFontColor`() {
                assertEquals(expected.cardUi.cardFontColor, cardData.cardUi!!.cardFontColor)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi securityCodeLength`() {
                assertEquals(expected.cardUi.securityCodeLength, cardData.cardUi!!.securityCodeLength)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi securityCodeLocation`() {
                assertEquals(expected.cardUi.securityCodeLocation, cardData.cardUi!!.securityCodeLocation)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi paymentMethodImageUrl`() {
                assertEquals(expected.cardUi.paymentMethodImageUrl, cardData.cardUi!!.paymentMethodImageUrl)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi issuerImageUrl`() {
                assertEquals(expected.cardUi.issuerImageUrl, cardData.cardUi!!.issuerImageUrl)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi cardPattern`() {
                assertEquals(expected.cardUi.cardPattern, cardData.cardUi!!.cardPattern)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi validation`() {
                assertEquals(expected.cardUi.validation, cardData.cardUi!!.validation)
            }

            @Test
            fun `RegisterCard to mapper CardData verify cardUi extraValidations`() {
                assertEquals(expected.cardUi.extraValidations, cardData.cardUi!!.extraValidations)
            }

            @Test
            fun `RegisterCard to mapper CardData verify paymentMethod paymentTypeId`() {
                assertEquals(expected.paymentMethod.paymentTypeId, cardData.paymentTypeId)
            }

            @Test
            fun `RegisterCard to mapper CardData verify paymentMethod name`() {
                assertEquals(expected.paymentMethod.name, cardData.name)
            }

            @Test
            fun `RegisterCard to mapper CardData verify issuerName`() {
                assertEquals(expected.issuers.first().name, cardData.issuerName)
            }

            @Test
            fun `RegisterCard to mapper CardData verify additionalSteps`() {
                assertEquals(expected.additionalSteps, cardData.additionalSteps)
            }
        }
    }
}
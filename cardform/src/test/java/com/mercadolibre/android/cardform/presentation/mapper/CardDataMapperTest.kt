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
    inner class GivenARegisterCardConversionIsRequestedCardData {

        @Nested
        @DisplayName("When prompted for a conversion from RegisterCard to CardData with success")
        inner class WhenPromptedForAConversionFromRegisterCardToCardDataWithSuccess {

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
                    null,
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
            fun `Then check the formTitle field received the correct value`() {
                assertEquals(expected.otherTexts.cardFormTitle, cardData.formTitle)
            }

            @Test
            fun `Then check the cardNumberLength field received the correct value`() {
                assertEquals(expected.cardUi.cardNumberLength, cardData.cardUi!!.cardNumberLength)
            }

            @Test
            fun `Then check the cardColor field received the correct value`() {
                assertEquals(expected.cardUi.cardColor, cardData.cardUi!!.cardColor)
            }

            @Test
            fun `Then check the cardFontColor field received the correct value`() {
                assertEquals(expected.cardUi.cardFontColor, cardData.cardUi!!.cardFontColor)
            }

            @Test
            fun `Then check the securityCodeLength field received the correct value`() {
                assertEquals(expected.cardUi.securityCodeLength, cardData.cardUi!!.securityCodeLength)
            }

            @Test
            fun `Then check the securityCodeLocation field received the correct value`() {
                assertEquals(expected.cardUi.securityCodeLocation, cardData.cardUi!!.securityCodeLocation)
            }

            @Test
            fun `Then check the paymentMethodImageUrl field received the correct value`() {
                assertEquals(expected.cardUi.paymentMethodImageUrl, cardData.cardUi!!.paymentMethodImageUrl)
            }

            @Test
            fun `Then check the issuerImageUrl field received the correct value`() {
                assertEquals(expected.cardUi.issuerImageUrl, cardData.cardUi!!.issuerImageUrl)
            }

            @Test
            fun `Then check the cardPattern field received the correct value`() {
                assertEquals(expected.cardUi.cardPattern, cardData.cardUi!!.cardPattern)
            }

            @Test
            fun `Then check the validation field received the correct value`() {
                assertEquals(expected.cardUi.validation, cardData.cardUi!!.validation)
            }

            @Test
            fun `Then check the extraValidations field received the correct value`() {
                assertEquals(expected.cardUi.extraValidations, cardData.cardUi!!.extraValidations)
            }

            @Test
            fun `Then check the paymentTypeId field received the correct value`() {
                assertEquals(expected.paymentMethod.paymentTypeId, cardData.paymentTypeId)
            }

            @Test
            fun `Then check the name field received the correct value`() {
                assertEquals(expected.paymentMethod.name, cardData.name)
            }

            @Test
            fun `Then check the ìssuerName field received the correct value`() {
                assertEquals(expected.issuers.first().name, cardData.issuerName)
            }

            @Test
            fun `Then check the addintionalSteps field received the correct value`() {
                assertEquals(expected.additionalSteps, cardData.additionalSteps)
            }
        }
    }
}
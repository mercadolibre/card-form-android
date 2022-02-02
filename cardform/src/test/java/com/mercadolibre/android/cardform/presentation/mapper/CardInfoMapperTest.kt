package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.presentation.model.CardStepInfo
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CardInfoMapperTest {

    @Nested
    @DisplayName("Given a request to convert Card Step Info to Card Info Body")
    inner class GivenARequestToConvertCardStepInfoToCardInfoBody {

        @Nested
        @DisplayName("When requested a successful conversion from CardStepInfo to CardInfoBody")
        inner class WhenRequestedASuccessfulConversionFromCardStepInfoToCardInfoBody {
            private val device = mockk<Device>(relaxed = true)
            private val subject = CardInfoMapper(device)
            private val expectedMonth = 12
            private val expectedYear = 2024
            private val expected = CardStepInfo(
                "1234123412341234", "name", "12/24",
                "123", "123456789", "12345678"
            )
            private val cardInfoBody = subject.map(expected)

            @Test
            fun `Then check the cardNumber field received the correct value`() {
                assertEquals(expected.cardNumber, cardInfoBody.cardNumber)
            }

            @Test
            fun `Then check the name field received the correct value`() {
                assertEquals(expected.nameOwner, cardInfoBody.cardHolder.name)
            }

            @Test
            fun `Then check the expirationMonth field received the correct value`() {
                assertEquals(expectedMonth, cardInfoBody.expirationMonth)
            }

            @Test
            fun `Then check the expirationYear field received the correct value`() {
                assertEquals(expectedYear, cardInfoBody.expirationYear)
            }

            @Test
            fun `Then check the securityCode field received the correct value`() {
                assertEquals(expected.code, cardInfoBody.securityCode)
            }

            @Test
            fun `Then check the type field received the correct value`() {
                assertEquals(expected.identificationId, cardInfoBody.cardHolder.identification.type)
            }

            @Test
            fun `Then check the number field received the correct value`() {
                assertEquals(expected.identificationNumber, cardInfoBody.cardHolder.identification.number)
            }
        }
    }
}
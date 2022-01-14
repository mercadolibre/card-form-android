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
    @DisplayName("Given a card step conversion is requested card body")
    inner class GivenCardStepConversionIsRequestedCardBody {

        @Nested
        @DisplayName("When requested a conversion")
        inner class WhenRequestedConversion {
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
            fun `CardStepInfo to mapper CardInfoBody verify cardNumber`() {
                assertEquals(expected.cardNumber, cardInfoBody.cardNumber)
            }

            @Test
            fun `CardStepInfo to mapper CardInfoBody verify name`() {
                assertEquals(expected.nameOwner, cardInfoBody.cardHolder.name)
            }

            @Test
            fun `CardStepInfo to mapper CardInfoBody verify month`() {
                assertEquals(expectedMonth, cardInfoBody.expirationMonth)
            }

            @Test
            fun `CardStepInfo to mapper CardInfoBody verify year`() {
                assertEquals(expectedYear, cardInfoBody.expirationYear)
            }

            @Test
            fun `CardStepInfo to mapper CardInfoBody verify securityCode`() {
                assertEquals(expected.code, cardInfoBody.securityCode)
            }

            @Test
            fun `CardStepInfo to mapper CardInfoBody verify type`() {
                assertEquals(expected.identificationId, cardInfoBody.cardHolder.identification.type)
            }

            @Test
            fun `CardStepInfo to mapper CardInfoBody verify number`() {
                assertEquals(expected.identificationNumber, cardInfoBody.cardHolder.identification.number)
            }
        }
    }
}
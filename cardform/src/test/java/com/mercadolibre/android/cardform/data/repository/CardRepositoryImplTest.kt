package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.network.exceptions.ExcludePaymentException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.Response
import java.io.IOException

internal class CardRepositoryImplTest {

    @Nested
    @DisplayName("Given an MP user makes a request")
    inner class GivenAnMPUserMakesARequest {

        @Nested
        @DisplayName("When get search infos cards with success")
        inner class WhenGetSearchInfosCardsWithSuccess {

            private val cardService: CardService = mockk(relaxed = true)
            private val registerCard = mockk<RegisterCard>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns Response.success(registerCard)
            }

            @Test
            fun `Then get card information with success`() {
                runBlocking {
                    Assertions.assertNotNull(subject.getCardInfo(""))
                }
            }
        }

        @Nested
        @DisplayName("When get search infos cards without success")
        inner class WhenGetSearchInfosCardsWithoutSuccess {

            private val cardService: CardService = mockk(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @Test
            fun `Then get card information with exception`() {
                runBlocking {
                    assertThrows<Exception> { subject.getCardInfo("") }
                }
            }
        }

        @Disabled
        @Nested
        @DisplayName("When search card info return error ExcludePaymentException")
        inner class WhenSearchCardInfoReturnExcludePaymentException {

            private val cardService: CardService = mockk(relaxed = true)
            private val response =  mockk<Response<RegisterCard>>(relaxed = true)
            private val responseBody = mockk<ResponseBody>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                every { response.code() } returns 400
                every { response.errorBody() } returns responseBody
                coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns response
            }

            @Test
            fun `Then return an ExcludePaymentException`() {
                runBlocking {
                    assertThrows<ExcludePaymentException> { subject.getCardInfo("") }
                }
            }
        }

        @Nested
        @DisplayName("When search card info return error IOException")
        inner class WhenSearchCardInfoReturnIOException {

            private val cardService: CardService = mockk(relaxed = true)
            private val response =  mockk<Response<RegisterCard>>(relaxed = true)
            private val responseBody = mockk<ResponseBody>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                every { response.code() } returns 404
                every { response.errorBody() } returns responseBody
                coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns response
            }

            @Test
            fun `Then return an IOException`() {
                runBlocking {
                    assertThrows<IOException> { subject.getCardInfo("") }
                }
            }
        }
    }

    @Nested
    @DisplayName("Given an ML user makes a request")
    inner class GivenAnMLUserMakesARequest {

        @Nested
        @DisplayName("When get search infos cards with success")
        inner class WhenGetSearchInfosCardsWithSuccess {

            private val cardService: CardService = mockk(relaxed = true)
            private val cardInfoDto: CardInfoDto = mockk(relaxed = true)
            private val registerCard = mockk<RegisterCard>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "checkout-on", cardInfoDto)

            @BeforeEach
            fun setUp() {
                coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns Response.success(registerCard)
            }

            @Test
            fun `Then return card information with success`() {
                runBlocking {
                    Assertions.assertNotNull(subject.getCardInfo("424242"))
                }
            }
        }

        @Nested
        @DisplayName("When get search infos cards without success")
        inner class WhenGetSearchInfosCardsWithoutSuccess {

            private val cardService: CardService = mockk(relaxed = true)
            private val cardInfoDto: CardInfoDto = mockk(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "checkout-on", cardInfoDto)

            @Test
            fun `Then return card information with exception`() {
                runBlocking {
                    assertThrows<Exception> { subject.getCardInfo("") }
                }
            }
        }

        @Disabled
        @Nested
        @DisplayName("When search card info return error ExcludePaymentException")
        inner class WhenSearchCardInfoReturnExcludePaymentException {

            private val cardService: CardService = mockk(relaxed = true)
            private val response =  mockk<Response<RegisterCard>>(relaxed = true)
            private val responseBody = mockk<ResponseBody>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                every { response.code() } returns 400
                every { response.errorBody() } returns responseBody
                coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns response
            }

            @Test
            fun `Then return an ExcludePaymentException`() {
                runBlocking {
                    assertThrows<ExcludePaymentException> { subject.getCardInfo("") }
                }
            }
        }

        @Disabled
        @Nested
        @DisplayName("When search card info return error IOException")
        inner class WhenSearchCardInfoReturnIOException {

            private val cardService: CardService = mockk(relaxed = true)
            private val response =  mockk<Response<RegisterCard>>(relaxed = true)
            private val responseBody = mockk<ResponseBody>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                every { response.code() } returns 404
                every { response.errorBody() } returns responseBody
                coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns response
            }

            @Test
            fun `Then return an IOException`() {
                runBlocking {
                    assertThrows<IOException> { subject.getCardInfo("") }
                }
            }
        }
    }
}

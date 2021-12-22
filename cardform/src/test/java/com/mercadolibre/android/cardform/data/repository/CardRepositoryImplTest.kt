package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.network.exceptions.ExcludePaymentException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.jupiter.api.*
import retrofit2.Response
import java.io.IOException


internal class CardRepositoryImplTest {

    @Nested
    @DisplayName("Given an MP user makes a request")
    inner class GivenAnMPUserMakesARequest {

        @Nested
        @DisplayName("When get search infos cards")
        inner class WhenGetSearchInfosCards {

            private val cardService: CardService = mockk(relaxed = true)
            private val registerCard = mockk<RegisterCard>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @Test
            fun `Get card information with success`() {
                coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns Response.success(registerCard)
                runBlocking {
                    Assertions.assertNotNull(subject.getCardInfo(""))
                }
            }

            @Test
            fun `Get card information with exception`() {
                runBlocking {
                    assertThrows<Exception> { subject.getCardInfo("") }
                }
            }
        }

        // TODO: Melhorar class repository
        @Nested
        @DisplayName("When search card info return error ExcludePaymentException")
        inner class WhenSearchCardInfoReturnExcludePaymentException {

            private val cardService: CardService = mockk(relaxed = true)
            val response =  mockk<Response<RegisterCard>>(relaxed = true)
            val responseBody = mockk<ResponseBody>(relaxed = true)
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

        // TODO: Melhorar class repository
        @Nested
        @DisplayName("When search card info return error IOException")
        inner class WhenSearchCardInfoReturnIOException {

            private val cardService: CardService = mockk(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                val response =  mockk<Response<RegisterCard>>(relaxed = true)
                val responseBody = mockk<ResponseBody>(relaxed = true)
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
        @DisplayName("When get search infos cards")
        inner class WhenGetSearchInfosCards {

            private val cardService: CardService = mockk(relaxed = true)
            private val cardInfoDto: CardInfoDto = mockk(relaxed = true)
            private val registerCard = mockk<RegisterCard>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "checkout-on", cardInfoDto)

            @Test
            fun `Get card information with success`() {
                runBlocking {
                    coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns Response.success(registerCard)
                    Assertions.assertNotNull(subject.getCardInfo("424242"))
                }
            }

            @Test
            fun `Get card information with exception`() {
                runBlocking {
                    assertThrows<Exception> { subject.getCardInfo("") }
                }
            }
        }

        // TODO: Melhorar class repository
        @Nested
        @DisplayName("When search card info return error ExcludePaymentException")
        inner class WhenSearchCardInfoReturnExcludePaymentException {

            private val cardService: CardService = mockk(relaxed = true)
            val response =  mockk<Response<RegisterCard>>(relaxed = true)
            val responseBody = mockk<ResponseBody>(relaxed = true)
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

        // TODO: Melhorar class repository
        @Nested
        @DisplayName("When search card info return error IOException")
        inner class WhenSearchCardInfoReturnIOException {

            private val cardService: CardService = mockk(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                val response =  mockk<Response<RegisterCard>>(relaxed = true)
                val responseBody = mockk<ResponseBody>(relaxed = true)
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

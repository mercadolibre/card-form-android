package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.CoroutinesTestExtension
import com.mercadolibre.android.cardform.data.model.body.CardInfoDto
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.service.CardService
import com.mercadolibre.android.cardform.network.exceptions.BusinessException
import com.mercadolibre.android.cardform.network.exceptions.ExcludePaymentException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.Response
import android.accounts.Account
import io.mockk.MockKStaticScope
import org.json.JSONObject


internal class CardRepositoryImplTest {

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @Nested
    @DisplayName("Given an MP user makes a request")
    inner class TestUserMP {

        @Nested
        @DisplayName("When get search infos cards")
        inner class InfoCards {

            private val cardService: CardService = mockk(relaxed = true)
            private val registerCard = mockk<RegisterCard>(relaxed = true)
            private val responseBody = mockk<ResponseBody>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "instore", null)

            @BeforeEach
            fun setUp() {
                MockKAnnotations.init(this)
            }

            @Test
            fun `Get card information`() {
                runBlocking {
                    coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns Response.success(registerCard)

                    val retorno: RegisterCard?
                    subject.getCardInfo("").let {
                        retorno = it
                    }
                    Assertions.assertNotNull(retorno)
                }
            }

            @Test
            fun `Get info card error throw ExcludePaymentException`() {
                runBlocking {

                    val aResponse: Response<RegisterCard> = Response.error(
                        400,
                        ResponseBody.create(
                            MediaType.parse("application/json"),
                            "{}"
//                            "{\"message\": \"The transaction does not accept this payment method\",\"error\": \"excluded_payment_type_error\",\"status\": 400,\"user_error_message\": \"\"}"
                        )
                    )

                    coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns aResponse

                    assertThrows<Exception> {
                        runBlocking {
                            subject.getCardInfo("")
                        }
                    }
                }

//                ResponseBody.create(
//                    MediaType.parse("application/json;charset=utf-8"),
//                    "{\"message\":\"The transaction does not accept this payment method.\"," +
//                            "\"error\":\"excluded_payment_type_error\"," +
//                            "\"status\":400," +
//                            "\"user_error_message\":\"\"}\"")
            }

            @Test
            fun `Get info card error`() {
                runBlocking {

                    coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns Response.error(
                        404,
                        ResponseBody.create(null, "")
                    )

                    assertThrows<Exception> {
                        runBlocking {
                            subject.getCardInfo("")
                        }
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("Given an ML user makes a request")
    inner class TestUserML {

        @Nested
        @DisplayName("When get search infos cards")
        inner class InfoCards {

            private val cardService: CardService = mockk(relaxed = true)
            private val cardInfoDto: CardInfoDto = mockk(relaxed = true)
            private val registerCard = mockk<RegisterCard>(relaxed = true)
            private val subject = CardRepositoryImpl(cardService, "MLA", null, "checkout-on", cardInfoDto)

            @BeforeEach
            fun setUp() {
                MockKAnnotations.init(this)
            }

            @Test
            fun `Get card information`() {
                runBlocking {

                    coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns Response.success(
                        registerCard
                    )

                    val retorno: RegisterCard?
                    subject.getCardInfo("424242").let {
                        retorno = it
                    }

                    Assertions.assertNotNull(retorno)
                }
            }

            @Test
            fun `Get info card error throw ExcludePaymentException`() {
                runBlocking {

                    coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns Response.error(
                        400,
                        ResponseBody.create(null, "")
                    )

                    assertThrows<Exception> {
                        runBlocking {
                            subject.getCardInfo("")
                        }
                    }
                }
            }

            @Test
            fun `Get info card error`() {
                runBlocking {

                    coEvery { cardService.getCardInfoAsyncFromMarketplace(any(), any()) } returns Response.error(
                        404,
                        ResponseBody.create(null, "")
                    )

                    assertThrows<Exception> {
                        runBlocking {
                            subject.getCardInfo("")
                        }
                    }
                }
            }
        }
    }
}

package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.TestContextProvider
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.model.response.CardToken
import com.mercadolibre.android.cardform.data.service.TokenizeService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.Response

internal class TokenizeRepositoryImplTest {

    @Nested
    @DisplayName("Given a user requests tokenization")
    inner class GivenAUserRequestsTokenization {

        @Nested
        @DisplayName("When to create async token with success")
        inner class WhenToCreateAsyncTokenWithSuccess {

            private val tokenizeService = mockk<TokenizeService>(relaxed = true)
            private val cardInfoBody = mockk<CardInfoBody>(relaxed = true)
            private val cardToken = mockk<CardToken>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val subject = TokenizeRepositoryImpl(tokenizeService, contextProvider)

            @BeforeEach
            fun setUp() {
                coEvery { tokenizeService.createTokenAsync(any()) } returns Response.success(cardToken)
            }

            @Test
            fun `Then tokenize card with success`() {
                runBlocking {
                    assertNotNull(subject.tokenizeCard(cardInfoBody))
                }
            }
        }

        @Nested
        @DisplayName("When to create async token with failure")
        inner class WhenToCreateAsyncTokenWithFailure {

            private val tokenizeService = mockk<TokenizeService>(relaxed = true)
            private val cardInfoBody = mockk<CardInfoBody>(relaxed = true)
            private val response = mockk<Response<CardToken>>(relaxed = true)
            private val responseBody = mockk<ResponseBody>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val subject = TokenizeRepositoryImpl(tokenizeService, contextProvider)

            @BeforeEach
            fun setUp() {
                every { response.code() } returns 400
                every { response.errorBody() } returns responseBody
                coEvery { tokenizeService.createTokenAsync(any()) } returns response
            }

            @Test
            fun `Then tokenize card with failure`() {
                runBlocking {
                    assertThrows<Exception> { subject.tokenizeCard(cardInfoBody) }
                }
            }
        }
    }
}
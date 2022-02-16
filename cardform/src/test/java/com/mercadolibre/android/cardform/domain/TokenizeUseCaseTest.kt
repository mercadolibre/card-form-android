package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.TestContextProvider
import com.mercadolibre.android.cardform.data.model.body.CardInfoBody
import com.mercadolibre.android.cardform.data.repository.TokenizeRepositoryImpl
import com.mercadolibre.android.cardform.data.service.TokenizeService
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TokenizeUseCaseTest {

    @Nested
    @DisplayName("Given a user requests tokenization")
    inner class GivenAUserRequestsTokenization {

        @Nested
        @DisplayName("When to create async token with success")
        inner class WhenToCreateAsyncTokenWithSuccess {

            private val tokenizeService = mockk<TokenizeService>(relaxed = true)
            private val cardInfoBody = mockk<CardInfoBody>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val tokenizeRepositoryImpl = TokenizeRepositoryImpl(tokenizeService, contextProvider)
            private val subject = TokenizeUseCase(tokenizeRepositoryImpl)

            @Test
            fun `Then tokenize card with success`() {
                runBlocking {
                    Assertions.assertNotNull(subject.execute(cardInfoBody))
                }
            }
        }
    }
}
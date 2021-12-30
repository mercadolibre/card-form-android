package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.TestContextProvider
import com.mercadolibre.android.cardform.data.service.InscriptionService
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class InscriptionRepositoryImplTest {

    @Nested
    @DisplayName("Given a user get record data")
    inner class GivenAUserGetCompletionRecordData {

        @Nested
        @DisplayName("When get record data with success")
        inner class WhenGetCompletionRecordDataWithSuccess {

            private val inscriptionService = mockk<InscriptionService>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val subject = InscriptionRepositoryImpl(inscriptionService, contextProvider)

            @Test
            fun `Then get registration data successfully`() {
                runBlocking {
                    assertNotNull(subject.getInscriptionData())
                }
            }
        }
    }
}
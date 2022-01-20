package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.TestContextProvider
import com.mercadolibre.android.cardform.data.repository.InscriptionRepositoryImpl
import com.mercadolibre.android.cardform.data.service.InscriptionService
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class InscriptionUseCaseTest {

    @Nested
    @DisplayName("Given a user initialize the registration of cadastral data")
    inner class GivenAUserInitializeTheRegistrationOfCadastralData {

        @Nested
        @DisplayName("When registration data was successfully entered")
        inner class WhenRegistrationDataWasSuccessfullyEntered {

            private val contextProvider = TestContextProvider()
            private val inscriptionService = mockk<InscriptionService>(relaxed = true)
            private val inscriptionRepositoryImpl = InscriptionRepositoryImpl(inscriptionService, contextProvider)
            private val param = mockk<Unit>()
            private val subject = InscriptionUseCase(inscriptionRepositoryImpl)

            @Test
            fun `Then get registration data successfully`() {
                runBlocking {
                    Assertions.assertNotNull(subject.execute(param))
                }
            }
        }
    }
}
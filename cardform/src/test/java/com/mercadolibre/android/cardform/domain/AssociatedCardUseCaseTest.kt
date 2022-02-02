package com.mercadolibre.android.cardform.domain

import com.mercadolibre.android.cardform.TestContextProvider
import com.mercadolibre.android.cardform.data.model.request.AssociatedCardParam
import com.mercadolibre.android.cardform.data.repository.CardAssociationRepositoryImpl
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AssociatedCardUseCaseTest {

    @Nested
    @DisplayName("Given away that I add a new card")
    inner class GivenAwayThatAddNewCard {

        @Nested
        @DisplayName("When the card associated is activated")
        inner class WhenTheCardAssociatedIsSuccessful {

            private val associatedCardParam = mockk<AssociatedCardParam>(relaxed = true)
            private val cardAssociationService = mockk<CardAssociationService>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val cardAssociationRepositoryImpl = CardAssociationRepositoryImpl(cardAssociationService,
                acceptThirdPartyCard = true,
                activateCard = true,
                contextProvider = contextProvider
            )
            private val subject = AssociatedCardUseCase(cardAssociationRepositoryImpl)

            @Test
            fun `Then the associated card is activated`() {
                runBlocking {
                    assertNotNull(subject.execute(associatedCardParam))
                }
            }
        }
    }
}
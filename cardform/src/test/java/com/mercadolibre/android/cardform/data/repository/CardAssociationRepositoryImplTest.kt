package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.TestContextProvider
import com.mercadolibre.android.cardform.data.service.CardAssociationService
import com.mercadolibre.android.cardform.domain.AssociatedCardParam
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CardAssociationRepositoryImplTest {

    @Nested
    @DisplayName("Given Card membership request")
    inner class GivenCardMembershipRequest {

        @Nested
        @DisplayName("When the card associated is activated")
        inner class WhenTheCardAssociatedIsSuccessful {

            private val cardAssociationService = mockk<CardAssociationService>(relaxed = true)
            private val associatedCardParam = mockk<AssociatedCardParam>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val subject = CardAssociationRepositoryImpl(cardAssociationService,
                acceptThirdPartyCard = true,
                activateCard = true,
                contextProvider = contextProvider
            )

            @Test
            fun `Then the associated card is activated`() {
                runBlocking {
                    Assertions.assertNotNull(subject.associateCard(associatedCardParam))
                }
            }
        }

        @Nested
        @DisplayName("When the card associated is not activated")
        inner class WhenTheCardAssociatedIsNotSuccessful {
            private val cardAssociationService = mockk<CardAssociationService>(relaxed = true)
            private val associatedCardParam = mockk<AssociatedCardParam>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val subject = CardAssociationRepositoryImpl(cardAssociationService,
                acceptThirdPartyCard = true,
                activateCard = false,
                contextProvider = contextProvider
            )

            @Test
            fun `Then the associated card is not activated`() {
                runBlocking {
                    Assertions.assertNotNull(subject.associateCard(associatedCardParam))
                }
            }
        }

        @Nested
        @DisplayName("When the card not associated is not activated")
        inner class WhenTheCardNotAssociatedIsNotSuccessful {

            private val cardAssociationService = mockk<CardAssociationService>(relaxed = true)
            private val associatedCardParam = mockk<AssociatedCardParam>(relaxed = true)
            private val contextProvider = TestContextProvider()
            private val subject = CardAssociationRepositoryImpl(cardAssociationService,
                acceptThirdPartyCard = false,
                activateCard = false,
                contextProvider = contextProvider
            )

            @Test
            fun `Then the unassociated card is not unactivated`() {
                runBlocking {
                    Assertions.assertNotNull(subject.associateCard(associatedCardParam))
                }
            }
        }
    }
}
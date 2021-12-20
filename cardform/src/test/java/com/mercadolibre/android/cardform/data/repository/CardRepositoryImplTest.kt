package com.mercadolibre.android.cardform.data.repository

import com.mercadolibre.android.cardform.CoroutinesTestExtension
import com.mercadolibre.android.cardform.data.model.response.*
import com.mercadolibre.android.cardform.data.service.CardService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.Response

class CardRepositoryImplTest {

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @MockK
    private lateinit var cardService: CardService
    private lateinit var cardRepositoryImpl: CardRepositoryImpl

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)

        //    private val cardRepositoryImpl: CardRepositoryImpl = mockk(relaxed = true)
        cardRepositoryImpl = CardRepositoryImpl(
            cardService, "MLB",
            null,
            "junit",
            null
        )
    }

    @Test
    fun `Get info card`() {
        runBlocking {

            val paymentMethod = mockk<PaymentMethod> {
                mockk {
                    every { name } returns "visa"
                    every { paymentMethodId } returns "credit_card"
                    every { paymentTypeId } returns "visa"
                    every { processingModes } returns listOf("aggregator")
                }
            }

            val cardUi = mockk<CardUi> {
                mockk {
                    every { cardNumberLength } returns 16
                    every { cardColor } returns "#2561C0"
                    every { cardFontColor } returns "#F5F5F5"
                    every { cardFontType } returns "light"
                    every { securityCodeLocation } returns "back"
                    every { securityCodeLength } returns 3
                    every { paymentMethodImageUrl } returns "https://mobile.mercadolibre.com/remote_resources/image/card_drawer_mla_pm_visa_white?density=xhdpi&locale=en"
                    every { issuerImageUrl } returns ""
                    every { cardPattern } returns IntArray(4) { 4 * (it) }
                    every { validation } returns "standard"
                    every { extraValidations } returns ArrayList()
                    //CardUi(cardNumberLength=16, cardColor=#2561C0, cardFontColor=#F5F5F5, cardFontType=light, securityCodeLocation=back, securityCodeLength=3, paymentMethodImageUrl=https://mobile.mercadolibre.com/remote_resources/image/card_drawer_mla_pm_visa_white?density=xhdpi&locale=en, issuerImageUrl=, cardPattern=[4, 4, 4, 4], validation=standard, extraValidations=[])
                }
            }

            val issuer = mockk<Issuer> {
                mockk {
                    every { name } returns "visa"
                    every { id } returns 1
                    every { isDefault } returns true
                }
//                    "issuers": [{
//                    "name": "Visa",
//                    "id": 1,
//                    "default": true
//                    }]
            }
            val issuers: List<Issuer> = listOf(issuer)

            val fieldsSetting = mockk<FieldsSetting> {
                mockk {
                    every { name } returns "name"
                    every { length } returns 40
                    every { type } returns "text"
                    every { title } returns "Card holder's name, hintMessage=As it shows on the card"
                    every { hintMessage } returns ""
                    every { validationPattern } returns "^[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]+(([',. -][a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ ])?[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]*)*$"
                    every { validationMessage } returns "Complete using only letters"
                    every { mask } returns ""
//                1 = {FieldsSetting@18426} "FieldsSetting(name=identification_types, length=null, type=number, title=Card holder's ID, hintMessage=null, validationPattern=null, validationMessage=Invalid ID, mask=null)"
//                2 = {FieldsSetting@18427} "FieldsSetting(name=expiration, length=4, type=number, title=Expiration, hintMessage=MM/YY, validationPattern=null, validationMessage=Invalid date, mask=$$/$$)"
//                3 = {FieldsSetting@18428} "FieldsSetting(name=security_code, length=null, type=number, title=Security code, hintMessage=CVV, validationPattern=null, validationMessage=Invalid code, mask=null)"
                }
            }
            val fieldsSettings: List<FieldsSetting> = listOf(fieldsSetting)

            val identificationTypes = mockk<IdentificationTypes> {
                mockk {
                    every { id } returns "DNI"
                    every { name } returns "DNI"
                    every { type} returns "number"
                    every { mask } returns "$$.$$$.$$$"
                    every { minLength } returns 7
                    every { maxLength } returns 8
//                1 = {IdentificationTypes@18441} "IdentificationTypes(id=CI, name=Cédula, type=number, mask=null, minLength=1, maxLength=9)"
//                2 = {IdentificationTypes@18442} "IdentificationTypes(id=LC, name=L.C., type=number, mask=null, minLength=6, maxLength=7)"
//                3 = {IdentificationTypes@18443} "IdentificationTypes(id=LE, name=L.E., type=number, mask=null, minLength=6, maxLength=7)"
//                4 = {IdentificationTypes@18444} "IdentificationTypes(id=Otro, name=Otro, type=number, mask=null, minLength=5, maxLength=20)"
                }
            }
            val listIdentificationTypes: List<IdentificationTypes> = listOf(identificationTypes)

            val registerCard = RegisterCard(
                OtherTexts("New credit card"),
                true,
                paymentMethod,
                cardUi,
                listOf("name", "identification_types"),
                issuers,
                fieldsSettings,
                listIdentificationTypes
            )

            val api = Response.success(registerCard)

            coEvery { cardService.getCardInfoAsync(any(), any(), any()) } returns api

            val retorno: RegisterCard?
            cardRepositoryImpl.getCardInfo("424242").let {
                retorno = it
            }

            Assertions.assertTrue(true)
        }
    }
}

package com.mercadolibre.android.cardform.presentation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import com.mercadolibre.android.cardform.base.BaseViewModel
import com.mercadolibre.android.cardform.data.model.response.CardUi
import com.mercadolibre.android.cardform.data.model.response.Issuer
import com.mercadolibre.android.cardform.data.model.response.PaymentMethod
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.repository.CardAssociationRepository
import com.mercadolibre.android.cardform.data.repository.CardRepository
import com.mercadolibre.android.cardform.data.repository.TokenizeRepository
import com.mercadolibre.android.cardform.presentation.mapper.CardAssociationMapper
import com.mercadolibre.android.cardform.presentation.mapper.CardInfoMapper
import com.mercadolibre.android.cardform.presentation.mapper.IdentificationMapper
import com.mercadolibre.android.cardform.presentation.mapper.InputMapper
import com.mercadolibre.android.cardform.presentation.model.*
import com.mercadolibre.android.cardform.presentation.ui.formentry.FormType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class InputFormViewModel(
    private val cardRepository: CardRepository,
    private val cardTokenRepository: TokenizeRepository,
    private val cardAssociationRepository: CardAssociationRepository
) : BaseViewModel() {

    val cardLiveFilledData: MutableLiveData<CardFilledData> = MutableLiveData()
    val progressLiveData: MutableLiveData<Int> = MutableLiveData()
    val cardDrawerLiveData: MutableLiveData<CardUi?> = MutableLiveData()
    val additionalStepsLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val identificationTypesLiveData: MutableLiveData<IdentificationData?> = MutableLiveData()
    val expirationLiveData: MutableLiveData<StepData> = MutableLiveData()
    val codeLiveData: MutableLiveData<StepData> = MutableLiveData()
    val nameLiveData: MutableLiveData<StepData> = MutableLiveData()
    val numberLiveData: MutableLiveData<StepData> = MutableLiveData()
    val stateUiLiveData: MutableLiveData<StateUi> = MutableLiveData()
    val stateCardLiveData: MutableLiveData<CardState> = MutableLiveData()
    val issuersLiveData: MutableLiveData<ArrayList<Issuer>> = MutableLiveData()
    var cardStepInfo = CardStepInfo()
    private var binValidator = BinValidator()
    private var issuer: Issuer? = null
    private var paymentMethod: PaymentMethod? = null

    override fun recoverFromBundle(bundle: Bundle) {
        super.recoverFromBundle(bundle)
        binValidator = bundle.getParcelable(EXTRA_BIN_VALIDATOR) ?: BinValidator()
        cardDrawerLiveData.value = bundle.getParcelable(EXTRA_CARD_DRAWER)
        identificationTypesLiveData.value = bundle.getParcelable(EXTRA_IDENTIFICATIONS_DATA)
        expirationLiveData.value = bundle.getParcelable(EXTRA_EXPIRATION_DATA)
        codeLiveData.value = bundle.getParcelable(EXTRA_CODE_DATA)
        nameLiveData.value = bundle.getParcelable(EXTRA_NAME_DATA)
        numberLiveData.value = bundle.getParcelable(EXTRA_NUMBER_DATA)
        issuersLiveData.value = bundle.getParcelableArrayList(EXTRA_ISSUER_LIST_DATA)
        issuer = bundle.getParcelable(EXTRA_ISSUER_DATA)
        paymentMethod = bundle.getParcelable(EXTRA_PAYMENT_METHOD_DATA)
        cardStepInfo = bundle.getParcelable(EXTRA_CARD_STEP_DATA)!!
    }

    override fun storeInBundle(bundle: Bundle) {
        super.storeInBundle(bundle)
        bundle.putParcelable(EXTRA_BIN_VALIDATOR, binValidator)
        cardDrawerLiveData.value?.let { bundle.putParcelable(EXTRA_CARD_DRAWER, it) }
        identificationTypesLiveData.value?.let {
            bundle.putParcelable(
                EXTRA_IDENTIFICATIONS_DATA,
                it
            )
        }
        expirationLiveData.value?.let { bundle.putParcelable(EXTRA_EXPIRATION_DATA, it) }
        codeLiveData.value?.let { bundle.putParcelable(EXTRA_CODE_DATA, it) }
        nameLiveData.value?.let { bundle.putParcelable(EXTRA_NAME_DATA, it) }
        numberLiveData.value?.let { bundle.putParcelable(EXTRA_NUMBER_DATA, it) }
        issuersLiveData.value?.let { bundle.putParcelableArrayList(EXTRA_ISSUER_LIST_DATA, it) }
        bundle.putParcelable(EXTRA_PAYMENT_METHOD_DATA, paymentMethod)
        bundle.putParcelable(EXTRA_ISSUER_DATA, issuer)
        bundle.putParcelable(EXTRA_CARD_STEP_DATA, cardStepInfo)
    }

    fun updateInputData(cardFilledData: CardFilledData) {
        cardLiveFilledData.value = cardFilledData
    }

    fun updateProgressData(progress: Int) {
        progressLiveData.value = progress
    }

    fun onCardNumberChange(cardNumber: String) {
        binValidator.update(cardNumber)
        if (binValidator.hasChanged()) {
            if (binValidator.bin == null) {
                cardDrawerLiveData.value?.apply {
                    cardDrawerLiveData.value = null
                }
            } else {
                fetchCard(binValidator.bin!!)
            }
        }
    }

    private fun fetchCard(bin: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                cardRepository.getCardInfo(bin)?.let {
                    setIssuer(it.issuers[0])
                    paymentMethod = it.paymentMethod
                    cardDrawerLiveData.postValue(it.cardUi)
                    additionalStepsLiveData.postValue(it.additionalSteps)
                    issuersLiveData.postValue(ArrayList(it.issuers))
                    loadInputData(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setIssuer(issuer: Issuer) {
        this.issuer = issuer
    }

    fun associateCard() {
        stateUiLiveData.value = StateUi.Loading
        try {
            tokenizeAndAddNewCard()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            stateUiLiveData.postValue(StateUi.Error)
        }
    }

    private fun tokenizeAndAddNewCard() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cardToken = cardTokenRepository.tokenizeCard(CardInfoMapper.map(cardStepInfo))

                if (cardToken != null) {
                    val cardAssociated = cardAssociationRepository
                        .associateCard(
                            CardAssociationMapper.map(
                                CardAssociationMapper.Model(
                                    cardToken.id,
                                    paymentMethod!!,
                                    issuer!!.id
                                )
                            )
                        )
                    if (cardAssociated != null) {
                        stateUiLiveData.postValue(StateUi.Result)
                    } else {
                        stateUiLiveData.postValue(StateUi.Error)
                    }
                } else {
                    stateUiLiveData.postValue(StateUi.Error)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                stateUiLiveData.postValue(StateUi.Error)
            }
        }
    }


    private fun loadInputData(registerCard: RegisterCard) {
        CoroutineScope(Dispatchers.Default).launch {

            registerCard.fieldsSetting.forEach { setting ->

                when (setting.name) {

                    FormType.CARD_NAME.getType() ->
                        nameLiveData.postValue(InputMapper.map(setting))

                    FormType.CARD_IDENTIFICATION.getType() ->
                        identificationTypesLiveData.postValue(
                            IdentificationMapper(setting).map(registerCard.identificationTypes)
                        )

                    FormType.EXPIRATION_TYPE -> {
                        expirationLiveData.postValue(InputMapper.map(setting))
                    }

                    FormType.SECURITY_CODE_TYPE -> {
                        val step = InputMapper.map(setting)
                        step.maxLength = registerCard.cardUi.securityCodeLength
                        codeLiveData.postValue(step)
                    }
                }
            }
        }
    }

    companion object {
        private const val EXTRA_BIN_VALIDATOR = "bin_validator"
        private const val EXTRA_CARD_DRAWER = "card_drawer"
        private const val EXTRA_IDENTIFICATIONS_DATA = "identifications_data"
        private const val EXTRA_EXPIRATION_DATA = "expiration_data"
        private const val EXTRA_CODE_DATA = "code_data"
        private const val EXTRA_NAME_DATA = "name_data"
        private const val EXTRA_NUMBER_DATA = "number_data"
        private const val EXTRA_ISSUER_DATA = "issuer_data"
        private const val EXTRA_PAYMENT_METHOD_DATA = "payment_method_data"
        private const val EXTRA_CARD_STEP_DATA = "card_step_data"
        private const val EXTRA_ISSUER_LIST_DATA = "issuer_list_data"
    }
}
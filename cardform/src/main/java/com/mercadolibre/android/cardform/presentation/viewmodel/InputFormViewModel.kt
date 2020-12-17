package com.mercadolibre.android.cardform.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.os.Bundle
import com.mercadolibre.android.cardform.internal.LifecycleListener
import com.mercadolibre.android.cardform.base.BaseViewModel
import com.mercadolibre.android.cardform.base.getOrElse
import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.data.model.response.CardUi
import com.mercadolibre.android.cardform.data.model.response.Issuer
import com.mercadolibre.android.cardform.data.model.response.PaymentMethod
import com.mercadolibre.android.cardform.data.model.response.RegisterCard
import com.mercadolibre.android.cardform.data.model.response.*
import com.mercadolibre.android.cardform.data.repository.CardRepository
import com.mercadolibre.android.cardform.domain.AssociatedCardParam
import com.mercadolibre.android.cardform.domain.AssociatedCardUseCase
import com.mercadolibre.android.cardform.domain.CardTokenModel
import com.mercadolibre.android.cardform.domain.TokenizeUseCase
import com.mercadolibre.android.cardform.presentation.extensions.hasConnection
import com.mercadolibre.android.cardform.presentation.mapper.*
import com.mercadolibre.android.cardform.presentation.model.*
import com.mercadolibre.android.cardform.presentation.model.StateUi.UiLoading
import com.mercadolibre.android.cardform.presentation.ui.ErrorUtil
import com.mercadolibre.android.cardform.presentation.ui.formentry.FormType
import com.mercadolibre.android.cardform.tracks.Tracker
import com.mercadolibre.android.cardform.tracks.model.TrackApiSteps
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.bin.*
import com.mercadolibre.android.cardform.tracks.model.flow.*
import com.mercadopago.android.px.addons.ESCManagerBehaviour
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

internal class InputFormViewModel(
    private val cardRepository: CardRepository,
    private val tokenizeUseCase: TokenizeUseCase,
    private val associatedCardUseCase: AssociatedCardUseCase,
    private val escManager: ESCManagerBehaviour,
    private val device: Device,
    val tracker: Tracker
) : BaseViewModel() {

    val cardLiveFilledData: MutableLiveData<CardFilledData> = MutableLiveData()
    val progressLiveData: MutableLiveData<Int> = MutableLiveData()
    val identificationTypesLiveData: MutableLiveData<IdentificationData?> = MutableLiveData()
    val expirationLiveData: MutableLiveData<StepData> = MutableLiveData()
    val codeLiveData: MutableLiveData<StepData> = MutableLiveData()
    val nameLiveData: MutableLiveData<StepData> = MutableLiveData()
    val numberLiveData: MutableLiveData<StepData> = MutableLiveData()
    val extraValidation: MutableLiveData<ArrayList<Validation>> = MutableLiveData()
    val stateUiLiveData: MutableLiveData<StateUi> = MutableLiveData()
    val stateCardLiveData: MutableLiveData<CardState> = MutableLiveData()
    val issuersLiveData: MutableLiveData<ArrayList<Issuer>> = MutableLiveData()
    val cardLiveData: MutableLiveData<CardData> = MutableLiveData()
    val updateCardData: MutableLiveData<CardUi> = MutableLiveData()
    var cardStepInfo = CardStepInfo()
    private var binValidator = BinValidator()
    private var issuer: Issuer? = null
    private var paymentMethod: PaymentMethod? = null
    private var escEnabled = true

    override fun recoverFromBundle(bundle: Bundle) {
        super.recoverFromBundle(bundle)
        binValidator = bundle.getParcelable(EXTRA_BIN_VALIDATOR) ?: BinValidator()
        identificationTypesLiveData.value = bundle.getParcelable(EXTRA_IDENTIFICATIONS_DATA)
        expirationLiveData.value = bundle.getParcelable(EXTRA_EXPIRATION_DATA)
        codeLiveData.value = bundle.getParcelable(EXTRA_CODE_DATA)
        nameLiveData.value = bundle.getParcelable(EXTRA_NAME_DATA)
        numberLiveData.value = bundle.getParcelable(EXTRA_NUMBER_DATA)
        extraValidation.value = bundle.getParcelableArrayList(EXTRA_VALIDATION_DATA)
        issuersLiveData.value = bundle.getParcelableArrayList(EXTRA_ISSUER_LIST_DATA)
        issuer = bundle.getParcelable(EXTRA_ISSUER_DATA)
        paymentMethod = bundle.getParcelable(EXTRA_PAYMENT_METHOD_DATA)
        cardStepInfo = bundle.getParcelable(EXTRA_CARD_STEP_DATA)!!
        cardLiveData.value = bundle.getParcelable(EXTRA_CARD_DATA)
        escEnabled = bundle.getBoolean(EXTRA_ESC_ENABLED)
    }

    override fun storeInBundle(bundle: Bundle) {
        super.storeInBundle(bundle)
        bundle.putParcelable(EXTRA_BIN_VALIDATOR, binValidator)
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
        extraValidation.value?.let { bundle.putParcelableArrayList(EXTRA_VALIDATION_DATA, it) }
        issuersLiveData.value?.let { bundle.putParcelableArrayList(EXTRA_ISSUER_LIST_DATA, it) }
        bundle.putParcelable(EXTRA_PAYMENT_METHOD_DATA, paymentMethod)
        bundle.putParcelable(EXTRA_ISSUER_DATA, issuer)
        bundle.putParcelable(EXTRA_CARD_STEP_DATA, cardStepInfo)
        cardLiveData.value?.let { bundle.putParcelable(EXTRA_CARD_DATA, it) }
        bundle.putBoolean(EXTRA_ESC_ENABLED, escEnabled)
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
                cardLiveData.value?.apply {
                    cardLiveData.value = null
                }
            } else {
                fetchCard(binValidator.bin!!)
            }
        }
    }

    fun retryFetchCard(context: Context?) {
        if (context.hasConnection()) {
            stateUiLiveData.value = UiLoading
            CoroutineScope(contextProvider.IO).launch {
                try {
                    cardRepository.getCardInfo(binValidator.bin!!)?.let {
                        loadRegisterCard(it)
                        stateUiLiveData.postValue(UiResult.EmptyResult)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    tracker.trackEvent(
                        ErrorTrack(
                            TrackApiSteps.BIN_NUMBER.getType(),
                            e.message.orEmpty()
                        )
                    )
                    with(ErrorUtil.createError(e)) {
                        when (this) {

                            is UiError.ConnectionError -> {
                                showError = false
                            }

                            is UiError.BusinessError -> {
                                tracker.trackEvent(BinUnknownTrack(binValidator.getLastValidBin()))
                            }
                        }
                        stateUiLiveData.postValue(this)
                    }
                    stateUiLiveData.postValue(ErrorUtil.createError(e))
                }
            }
        } else {
            stateUiLiveData.value = ErrorUtil.createError(UnknownHostException())
        }
    }

    private fun fetchCard(bin: String) {
        CoroutineScope(contextProvider.IO).launch {
            try {
                cardRepository.getCardInfo(bin)?.let {
                    loadRegisterCard(it)
                    stateUiLiveData.postValue(UiResult.EmptyResult)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                tracker.trackEvent(
                    ErrorTrack(
                        TrackApiSteps.BIN_NUMBER.getType(),
                        e.message.orEmpty()
                    )
                )
                with(ErrorUtil.createError(e)) {
                    when (this) {

                        is UiError.ConnectionError -> {
                            showError = false
                        }

                        is UiError.BusinessError -> {
                            tracker.trackEvent(BinUnknownTrack(binValidator.getLastValidBin()))
                        }
                    }
                    stateUiLiveData.postValue(this)
                }
            }
        }
    }

    private fun loadRegisterCard(registerCard: RegisterCard) {
        with(registerCard) {
            setIssuer(issuers[0])
            this@InputFormViewModel.escEnabled = escEnabled
            this@InputFormViewModel.paymentMethod = paymentMethod
            extraValidation.postValue(cardUi.extraValidations)
            cardLiveData.postValue(CardDataMapper.map(this))
            issuersLiveData.postValue(ArrayList(issuers))
            loadInputData(this)
            tracker.trackEvent(BinRecognizedTrack())
        }
    }

    fun hasLuhnValidation() = cardLiveData.value?.cardUi?.validation == "standard"

    private fun setIssuer(issuer: Issuer?) {
        this.issuer = issuer
    }

    fun updateIssuer(issuer: Issuer?) {
        val cardData = cardLiveData.value?.cardUi
        cardData?.issuerImageUrl = issuer?.imageUrl
        updateCardData.value = cardData
        setIssuer(issuer)
    }

    fun associateCard() {
        stateUiLiveData.value = UiLoading
        tokenizeAndAddNewCard()
    }

    private suspend fun getCardToken() = tokenizeUseCase
        .execute(CardInfoMapper(device).map(cardStepInfo))
        .getOrElse { throwable ->
            tracker.trackEvent(
                ErrorTrack(
                    TrackApiSteps.TOKEN.getType(),
                    throwable.message.orEmpty()
                )
            )
            stateUiLiveData.postValue(ErrorUtil.createError(throwable))
        }

    private suspend fun getCardAssociationId(cardTokenId: String) = associatedCardUseCase
        .execute(AssociatedCardParam(
            cardTokenId,
            paymentMethod!!.paymentMethodId,
            paymentMethod!!.paymentTypeId,
            issuer!!.id
        ))
        .getOrElse { throwable ->
            tracker.trackEvent(
                ErrorTrack(
                    TrackApiSteps.ASSOCIATION.getType(),
                    throwable.message.orEmpty()
                )
            )
            stateUiLiveData.postValue(ErrorUtil.createError(throwable))
        }

    private fun tokenizeAndAddNewCard() {
        CoroutineScope(contextProvider.Default).launch {
            lateinit var cardTokenModel: CardTokenModel
            getCardToken()?.let {
                cardTokenModel = it
                getCardAssociationId(cardTokenModel.id)
            }?.also { cardAssociationId ->

                if (escEnabled) {
                    escManager.saveESCWith(cardAssociationId, cardTokenModel.esc)
                }
                val onSuccess = {
                    tracker.trackEvent(SuccessTrack(
                        cardStepInfo.cardNumber.substring(0..5),
                        issuer?.id ?: 0,
                        paymentMethod?.paymentMethodId!!,
                        paymentMethod?.paymentTypeId!!
                    ))
                    stateUiLiveData.postValue(UiResult.CardResult(cardAssociationId))
                }

                withContext(Dispatchers.Main) {
                    lifecycleListener?.onCardAdded(
                        cardAssociationId,
                        object : LifecycleListener.Callback {
                            override fun onSuccess() {
                                onSuccess()
                            }

                            override fun onError() {
                                sendGenericError()
                            }
                        }) ?: onSuccess()
                }
            } ?: sendGenericError()
        }
    }

    private fun sendGenericError() {
        stateUiLiveData.postValue(ErrorUtil.createError())
    }

    private fun loadInputData(registerCard: RegisterCard) {
        CoroutineScope(contextProvider.Default).launch {
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

    fun trackInit() {
        tracker.trackEvent(InitTrack())
    }

    fun trackBack() {
        tracker.trackEvent(BackTrack())
    }

    companion object {
        private const val EXTRA_BIN_VALIDATOR = "bin_validator"
        private const val EXTRA_IDENTIFICATIONS_DATA = "identifications_data"
        private const val EXTRA_EXPIRATION_DATA = "expiration_data"
        private const val EXTRA_CODE_DATA = "code_data"
        private const val EXTRA_NAME_DATA = "name_data"
        private const val EXTRA_NUMBER_DATA = "number_data"
        private const val EXTRA_VALIDATION_DATA = "extra_validation_data"
        private const val EXTRA_ISSUER_DATA = "issuer_data"
        private const val EXTRA_PAYMENT_METHOD_DATA = "payment_method_data"
        private const val EXTRA_CARD_STEP_DATA = "card_step_data"
        private const val EXTRA_ISSUER_LIST_DATA = "issuer_list_data"
        private const val EXTRA_CARD_DATA = "card_data"
        private const val EXTRA_ESC_ENABLED = "esc_enabled"
    }
}
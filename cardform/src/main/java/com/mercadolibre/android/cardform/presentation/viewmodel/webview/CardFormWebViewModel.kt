package com.mercadolibre.android.cardform.presentation.viewmodel.webview

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.base.BaseViewModel
import com.mercadolibre.android.cardform.base.getOrElse
import com.mercadolibre.android.cardform.domain.*
import com.mercadolibre.android.cardform.domain.FinishInscriptionUseCase
import com.mercadolibre.android.cardform.domain.InscriptionUseCase
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.model.TokenizeAssociationModel
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import com.mercadolibre.android.cardform.service.CardFormServiceManager
import com.mercadolibre.android.cardform.tracks.CardFormTracker
import com.mercadolibre.android.cardform.tracks.model.TrackApiSteps
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.flow.BackTrack
import com.mercadolibre.android.cardform.tracks.model.flow.ErrorTrack
import com.mercadolibre.android.cardform.tracks.model.flow.InitTrack
import com.mercadolibre.android.cardform.tracks.model.flow.SuccessTrack
import com.mercadolibre.android.cardform.presentation.model.WebViewData
import com.mercadolibre.android.cardform.tracks.model.webview.WebViewTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val USER_FULL_NAME_EXTRA = "user_full_name"
private const val USER_IDENTIFICATION_NUMBER_EXTRA = "user_identification_number"
private const val USER_IDENTIFICATION_TYPE_EXTRA = "user_identification_type"
private const val TOKEN_DATA_EXTRA = "token_data"
private const val TBK_TOKEN_KEY = "TBK_TOKEN"

internal class CardFormWebViewModel(
    private val inscriptionUseCase: InscriptionUseCase,
    private val finishInscriptionUseCase: FinishInscriptionUseCase,
    private val tokenizeWebCardUseCase: TokenizeWebCardUseCase,
    private val associatedCardUseCase: AssociatedCardUseCase,
    private val tracker: CardFormTracker,
    private val cardFormServiceManager: CardFormServiceManager?,
    private val liveDataProvider: CardFormWebViewLiveDataProvider = CardFormWebViewLiveDataProvider,
    private val flowRetryProvider: FlowRetryProvider = FlowRetryProvider
) : BaseViewModel() {

    val webUiStateLiveData: LiveData<WebUiState>
        get() = liveDataProvider.webUiStateLiveData

    val screenStateLiveData: LiveData<ScreenState>
        get() = liveDataProvider.screenStateMutableLiveData

    val loadWebViewLiveData: LiveData<WebViewData>
        get() = liveDataProvider.loadWebViewMutableLiveData

    val canGoBackViewLiveData: LiveData<Boolean>
        get() = liveDataProvider.canGoBackMutableLiveData

    val cardResultLiveData: LiveData<String>
        get() = liveDataProvider.cardResultMutableLiveData

    val finishAssociationCardLiveData: LiveData<Unit>
        get() = liveDataProvider.finishAssociationCardMutableLiveData

    private var userFullName = ""
    private var userIdentificationNumber: String? = null
    private var userIdentificationType: String? = null
    private var tokenData = ""

    override fun recoverFromBundle(bundle: Bundle) {
        with(bundle) {
            getString(USER_FULL_NAME_EXTRA)?.let { userFullName = it }
            getString(USER_IDENTIFICATION_NUMBER_EXTRA)?.let { userIdentificationNumber = it }
            getString(USER_IDENTIFICATION_TYPE_EXTRA)?.let { userIdentificationType = it }
            getString(TOKEN_DATA_EXTRA)?.let { tokenData = it }
        }
    }

    override fun storeInBundle(bundle: Bundle) {
        with(bundle) {
            putString(USER_FULL_NAME_EXTRA, userFullName)
            putString(USER_IDENTIFICATION_NUMBER_EXTRA, userIdentificationNumber)
            putString(USER_IDENTIFICATION_TYPE_EXTRA, userIdentificationType)
            putString(TOKEN_DATA_EXTRA, tokenData)
        }
    }

    fun initInscription() {
        inscriptionUseCase.execute(Unit,
            success = {
                userFullName = it.fullName
                userIdentificationNumber = it.identifierNumber
                userIdentificationType = it.identifierType
                tokenData = it.token
                liveDataProvider.loadWebViewMutableLiveData.value =
                    WebViewData(
                        it.redirectUrl,
                        it.urlWebPay,
                        "$TBK_TOKEN_KEY=${tokenData}".toByteArray()
                    )
                tracker.trackEvent(WebViewTrack(it.urlWebPay))
            },
            failure = {
                tracker.trackEvent(
                    ErrorTrack(
                        TrackApiSteps.INIT_INSCRIPTION.getType(),
                        it.localizedMessage.orEmpty()
                    )
                )
                flowRetryProvider.setRetryFunction {
                    showProgressStartScreen()
                    initInscription()
                }
                showErrorState()
            })
    }

    fun finishInscription() {
        finishInscriptionUseCase.execute(tokenData,
            success = {
                tokenizeAndAssociateCard(
                    TokenizeAssociationModel(
                        it.cardNumberId,
                        it.truncCardNumber,
                        it.expirationMonth,
                        it.expirationYear,
                        it.cardNumberLength,
                        it.issuerId,
                        it.paymentMethodId,
                        it.paymentMethodType
                    )
                )
            },
            failure = {
                tracker.trackEvent(
                    ErrorTrack(
                        TrackApiSteps.FINISH_INSCRIPTION.getType(),
                        it.localizedMessage.orEmpty()
                    )
                )
                flowRetryProvider.setRetryFunction {
                    showProgressBackScreen()
                    finishInscription()
                }
                showErrorState()
            })
    }

    private fun tokenizeAndAssociateCard(model: TokenizeAssociationModel) {
        CoroutineScope(contextProvider.Default).launch {
            lateinit var cardTokenId: String
            getCardToken(model)?.let {
                cardTokenId = it
                getCardAssociationId(cardTokenId, model)
            }?.let { cardAssociationId ->
                withContext(Dispatchers.Main) {
                    coordinateResultCompletion(cardAssociationId)
                    tracker.trackEvent(
                        SuccessTrack(
                            model.truncCardNumber.substring(0..5),
                            model.issuerId,
                            model.paymentMethodId,
                            model.paymentMethodType
                        )
                    )
                }
            }
        }
    }

    private fun coordinateResultCompletion(cardAssociationId: String) {
        cardFormServiceManager?.let { serviceManager ->
            val data = Bundle().also {
                it.putString(CardForm.RESULT_CARD_ID_KEY, cardAssociationId)
            }
            serviceManager.onBindService(data) { finishProcessAssociationCard() }
        } ?: sendCardResult(cardAssociationId)
    }

    private suspend fun getCardToken(model: TokenizeAssociationModel) = run {
        tokenizeWebCardUseCase
            .execute(
                TokenizeWebCardParam(
                    model.cardNumberId,
                    model.truncCardNumber,
                    userFullName,
                    userIdentificationNumber,
                    userIdentificationType,
                    model.expirationMonth,
                    model.expirationYear,
                    model.cardNumberLength
                )
            )
            .getOrElse { throwable ->
                tracker.trackEvent(
                    ErrorTrack(
                        TrackApiSteps.TOKEN.getType(),
                        throwable.localizedMessage.orEmpty()
                    )
                )
                flowRetryProvider.setRetryFunction {
                    showProgressBackScreen()
                    tokenizeAndAssociateCard(model)
                }
                showErrorState()
            }
    }

    private suspend fun getCardAssociationId(
        cardTokenId: String,
        model: TokenizeAssociationModel
    ) = run {
        associatedCardUseCase.execute(
            AssociatedCardParam(
                cardTokenId,
                model.paymentMethodId,
                model.paymentMethodType,
                model.issuerId
            )
        ).getOrElse { throwable ->
            tracker.trackEvent(
                ErrorTrack(
                    TrackApiSteps.ASSOCIATION.getType(),
                    throwable.localizedMessage.orEmpty()
                )
            )
            flowRetryProvider.setRetryFunction {
                showProgressBackScreen()
                tokenizeAndAssociateCard(model)
            }
            showErrorState()
        }
    }

    private fun sendCardResult(cardId: String) {
        liveDataProvider.cardResultMutableLiveData.value = cardId
    }

    fun retry() {
        flowRetryProvider.retry()
    }

    fun showSuccessState() {
        liveDataProvider.webUiStateLiveData.value = WebUiState.WebSuccess
    }

    fun finishProcessAssociationCard() {
        liveDataProvider.finishAssociationCardMutableLiveData.value = Unit
    }

    fun showErrorState() {
        liveDataProvider.webUiStateLiveData.postValue(WebUiState.WebError)
    }

    fun showWebViewScreen() {
        liveDataProvider.screenStateMutableLiveData.value = ScreenState.WebViewState
    }

    fun showProgressBackScreen() {
        liveDataProvider.screenStateMutableLiveData.postValue(ScreenState.ProgressState)
        liveDataProvider.webUiStateLiveData.postValue(WebUiState.WebProgressBack)
    }

    fun showProgressStartScreen() {
        liveDataProvider.screenStateMutableLiveData.value = ScreenState.ProgressState
        liveDataProvider.webUiStateLiveData.value = WebUiState.WebProgressStart
    }

    fun canGoBack(canGoBack: Boolean) {
        liveDataProvider.canGoBackMutableLiveData.value = canGoBack
    }

    fun trackInit() {
        tracker.trackEvent(InitTrack(TrackSteps.WEB_VIEW.getType()))
    }

    fun trackBack() {
        tracker.trackEvent(BackTrack(TrackSteps.WEB_VIEW.getType()))
    }
}
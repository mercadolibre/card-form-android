package com.mercadolibre.android.cardform.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.cardform.base.BaseViewModel
import com.mercadolibre.android.cardform.base.getOrElse
import com.mercadolibre.android.cardform.domain.*
import com.mercadolibre.android.cardform.domain.FinishInscriptionUseCase
import com.mercadolibre.android.cardform.domain.InscriptionUseCase
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class CardFormWebViewModel(
    private val inscriptionUseCase: InscriptionUseCase,
    private val finishInscriptionUseCase: FinishInscriptionUseCase,
    private val tokenizeWebCardUseCase: TokenizeWebCardUseCase,
    private val associatedCardUseCase: AssociatedCardUseCase
) : BaseViewModel() {

    private val webUiStateMutableLiveData = MutableLiveData<WebUiState>()
    val webUiStateLiveData: LiveData<WebUiState>
        get() = webUiStateMutableLiveData
    private lateinit var userFullName: String
    private lateinit var userIdentificationNumber: String
    private lateinit var userIdentificationType: String

    private val screenStateMutableLiveData = MutableLiveData<ScreenState>()
    val screenStateLiveData: LiveData<ScreenState>
        get() = screenStateMutableLiveData

    private val loadWebViewMutableLiveData = MutableLiveData<Pair<String, ByteArray>>()
    val loadWebViewLiveData: LiveData<Pair<String, ByteArray>>
        get() = loadWebViewMutableLiveData

    private val canGoBackMutableLiveData = MutableLiveData<Boolean>()
    val canGoBackViewLiveData: LiveData<Boolean>
        get() = canGoBackMutableLiveData

    private val cardResultMutableLiveData = MutableLiveData<String>()
    val cardResultLiveData: LiveData<String>
        get() = cardResultMutableLiveData

    private var retryFunction: () -> Unit = {}

    fun initInscription() {
        inscriptionUseCase.execute(Unit,
            success = {
                userFullName = it.fullName
                userIdentificationNumber = it.identifierNumber
                userIdentificationType = it.identifierType
                loadWebViewMutableLiveData.value = (it.urlWebPay to it.token)
            },
            failure = {
                retryFunction = {
                    showProgressStartScreen()
                    initInscription()
                }
                showErrorState()
            })
    }

    fun finishInscription(token: String) {
        finishInscriptionUseCase.execute(token,
            success = {
                tokenizeAndAssociateCard(it)
            },
            failure = {
                retryFunction = {
                    showProgressBackScreen()
                    finishInscription(token)
                }
                showErrorState()
            })
    }

    private fun tokenizeAndAssociateCard(finishInscriptionModel: FinishInscriptionModel) {
        CoroutineScope(Dispatchers.Default).launch {
            lateinit var cardTokenId: String
            getCardToken(finishInscriptionModel)?.let {
                cardTokenId = it
                getCardAssociationId(cardTokenId, finishInscriptionModel)
            }?.let { cardAssociationId ->
                withContext(Dispatchers.Main) {
                    sendCardResult(cardAssociationId)
                }
            } ?: let {
                retryFunction = {
                    showProgressBackScreen()
                    tokenizeAndAssociateCard(finishInscriptionModel)
                }
            }
        }
    }

    private suspend fun getCardToken(model: FinishInscriptionModel) = run {
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
                showErrorState()
            }
    }

    private suspend fun getCardAssociationId(
        cardTokenId: String,
        model: FinishInscriptionModel
    ) = run {
        associatedCardUseCase.execute(
            AssociatedCardParam(
                cardTokenId,
                model.paymentMethodId,
                model.paymentMethodType,
                model.issuerId
            )
        ).getOrElse { throwable ->
            showErrorState()
        }
    }

    private fun sendCardResult(cardId: String) {
        cardResultMutableLiveData.value = cardId
    }

    fun retry() {
        retryFunction()
    }

    fun showSuccessState() {
        webUiStateMutableLiveData.value = WebUiState.WebSuccess
    }

    private fun showErrorState() {
        webUiStateMutableLiveData.postValue(WebUiState.WebError)
    }

    fun showWebViewScreen() {
        screenStateMutableLiveData.value = ScreenState.WebViewState
    }

    fun showProgressBackScreen() {
        screenStateMutableLiveData.postValue(ScreenState.ProgressState)
        webUiStateMutableLiveData.postValue(WebUiState.WebProgressBack)
    }

    fun showProgressStartScreen() {
        screenStateMutableLiveData.value = ScreenState.ProgressState
        webUiStateMutableLiveData.value = WebUiState.WebProgressStart
    }

    fun canGoBack(canGoBack: Boolean) {
        canGoBackMutableLiveData.value = canGoBack
    }
}
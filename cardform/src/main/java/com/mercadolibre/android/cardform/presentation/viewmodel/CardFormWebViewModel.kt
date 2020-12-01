package com.mercadolibre.android.cardform.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.cardform.base.BaseViewModel
import com.mercadolibre.android.cardform.base.getOrElse
import com.mercadolibre.android.cardform.base.orIfEmpty
import com.mercadolibre.android.cardform.domain.*
import com.mercadolibre.android.cardform.domain.FinishInscriptionUseCase
import com.mercadolibre.android.cardform.domain.InscriptionUseCase
import com.mercadolibre.android.cardform.presentation.mapper.CardInfoMapper
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class CardFormWebViewModel(
    private val inscriptionUseCase: InscriptionUseCase,
    private val finishInscriptionUseCase: FinishInscriptionUseCase,
    private val tokenizeWebCardUseCase: TokenizeWebCardUseCase,
    private val associatedCardUseCase: AssociatedCardUseCase,
    private val cardInfoMapper: CardInfoMapper
) : BaseViewModel() {

    private val webUiStateMutableLiveData = MutableLiveData<WebUiState>()
    val webUiStateLiveData: LiveData<WebUiState>
        get() = webUiStateMutableLiveData

    private val screenStateMutableLiveData = MutableLiveData<ScreenState>()
    val screenStateLiveData: LiveData<ScreenState>
        get() = screenStateMutableLiveData

    private val loadWebViewMutableLiveData = MutableLiveData<Pair<String, ByteArray>>()
    val loadWebViewLiveData: LiveData<Pair<String, ByteArray>>
        get() = loadWebViewMutableLiveData

    private val canGoBackMutableLiveData = MutableLiveData<Boolean>()
    val canGoBackViewLiveData: LiveData<Boolean>
        get() = canGoBackMutableLiveData

    private lateinit var token: ByteArray
    private var retryFunction: () -> Unit = {}

    fun initInscription(userName: String, userEmail: String, responseUrl: String) {
        inscriptionUseCase.execute(
            InscriptionParams(userName, userEmail, responseUrl),
            success = {
                token = it.token
                loadWebViewMutableLiveData.value = (it.urlWebPay to it.token)
            },
            failure = {
                Log.i("JORGE", it.localizedMessage.orIfEmpty("inscriptionUseCase"))
                retryFunction = {
                    showProgressStartScreen()
                    initInscription(userName, userEmail, responseUrl)
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
                Log.i("JORGE", it.localizedMessage.orIfEmpty("finishInscriptionUseCase"))
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
                showSuccessState()
                Log.i("JORGE", "CardAssociationId: $cardAssociationId")
            } ?: let { retryFunction = {
                showProgressBackScreen()
                tokenizeAndAssociateCard(finishInscriptionModel)
            } }
        }
    }

    private suspend fun getCardToken(finishInscriptionModel: FinishInscriptionModel) = run {
        tokenizeWebCardUseCase
            .execute(cardInfoMapper.map(finishInscriptionModel))
            .getOrElse { throwable ->
                Log.i("JORGE", throwable.localizedMessage.orIfEmpty("tokenizeUseCase"))
                showErrorState()
            }
    }

    private suspend fun getCardAssociationId(
        cardTokenId: String,
        finishInscriptionModel: FinishInscriptionModel
    ) = run {
        associatedCardUseCase.execute(
            AssociatedCardParam(
                cardTokenId,
                "redcompra",
                "debit_card",
                1048
            )
        ).getOrElse { throwable ->
            Log.i("JORGE", throwable.localizedMessage.orIfEmpty("cardAssociationUseCase"))
            showErrorState()
        }
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
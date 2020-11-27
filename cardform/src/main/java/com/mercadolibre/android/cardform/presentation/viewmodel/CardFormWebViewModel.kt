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
import com.mercadolibre.android.cardform.presentation.model.LoadingScreenState
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

    private val loadingScreenStateMutableLiveData = MutableLiveData<LoadingScreenState>()
    val loadingScreenStateLiveData: LiveData<LoadingScreenState>
        get() = loadingScreenStateMutableLiveData

    private lateinit var token: ByteArray

    fun initInscription(userName: String, userEmail: String, responseUrl: String) {
        webUiStateMutableLiveData.value = WebUiState.WebProgress
        inscriptionUseCase.execute(
            InscriptionParams(userName, userEmail, responseUrl),
            success = {
                token = it.token
                webUiStateMutableLiveData.value = WebUiState.WebSuccess(it.urlWebPay, token)
            },
            failure = {
                Log.i("JORGE", it.localizedMessage.orIfEmpty("inscriptionUseCase"))
                webUiStateMutableLiveData.value = WebUiState.WebError
            })
    }

    fun finishInscription(token: String) {
        finishInscriptionUseCase.execute(token,
            success = {
                webUiStateMutableLiveData.value = WebUiState.WebProgress
                tokenizeAndAssociateCard(it)
            },
            failure = {
                Log.i("JORGE", it.localizedMessage.orIfEmpty("finishInscriptionUseCase"))
                webUiStateMutableLiveData.value = WebUiState.WebError
            })
    }

    private fun tokenizeAndAssociateCard(finishInscriptionModel: FinishInscriptionModel) {
        CoroutineScope(Dispatchers.Default).launch {
            lateinit var cardTokenId: String
            getCardToken(finishInscriptionModel)?.let {
                cardTokenId = it
                getCardAssociationId(cardTokenId, finishInscriptionModel)
            }?.let { cardAssociationId ->
                Log.i("JORGE", "CardAssociationId: $cardAssociationId")
            }
        }
    }

    private suspend fun getCardToken(finishInscriptionModel: FinishInscriptionModel) = run {
        tokenizeWebCardUseCase
            .execute(cardInfoMapper.map(finishInscriptionModel))
            .getOrElse { throwable ->
                Log.i("JORGE", throwable.localizedMessage.orIfEmpty("tokenizeUseCase"))
                webUiStateMutableLiveData.postValue(WebUiState.WebError)
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
            webUiStateMutableLiveData.postValue(WebUiState.WebError)
        }
    }
}
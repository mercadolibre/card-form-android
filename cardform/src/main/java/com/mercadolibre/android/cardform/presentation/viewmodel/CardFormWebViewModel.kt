package com.mercadolibre.android.cardform.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.cardform.base.BaseViewModel
import com.mercadolibre.android.cardform.base.getOrElse
import com.mercadolibre.android.cardform.domain.*
import com.mercadolibre.android.cardform.domain.FinishInscriptionUseCase
import com.mercadolibre.android.cardform.domain.InscriptionUseCase
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import com.mercadolibre.android.cardform.tracks.Tracker
import com.mercadolibre.android.cardform.tracks.model.TrackApiSteps
import com.mercadolibre.android.cardform.tracks.model.flow.ErrorTrack
import com.mercadolibre.android.cardform.tracks.model.flow.SuccessTrack
import com.mercadolibre.android.cardform.tracks.model.webview.WebViewTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class CardFormWebViewModel(
    private val inscriptionUseCase: InscriptionUseCase,
    private val finishInscriptionUseCase: FinishInscriptionUseCase,
    private val tokenizeWebCardUseCase: TokenizeWebCardUseCase,
    private val associatedCardUseCase: AssociatedCardUseCase,
    val tracker: Tracker
) : BaseViewModel() {

    private val webUiStateMutableLiveData = MutableLiveData<WebUiState>()
    val webUiStateLiveData: LiveData<WebUiState>
        get() = webUiStateMutableLiveData
    private lateinit var userFullName: String
    private lateinit var userIdentificationNumber: String
    private lateinit var userIdentificationType: String

    fun initInscription() {
        webUiStateMutableLiveData.value = WebUiState.WebProgress
        inscriptionUseCase.execute(Unit,
            success = {
                userFullName = it.fullName
                userIdentificationNumber = it.identifierNumber
                userIdentificationType = it.identifierType
                webUiStateMutableLiveData.value = WebUiState.WebSuccess(
                    it.urlWebPay,
                    it.token,
                    it.redirectUrl
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
                tracker.trackEvent(
                    ErrorTrack(
                        TrackApiSteps.FINISH_INSCRIPTION.getType(),
                        it.localizedMessage.orEmpty()
                    )
                )
                webUiStateMutableLiveData.value = WebUiState.WebError
            })
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
                tracker.trackEvent(
                    ErrorTrack(
                        TrackApiSteps.TOKEN.getType(),
                        throwable.localizedMessage.orEmpty()
                    )
                )
                webUiStateMutableLiveData.postValue(WebUiState.WebError)
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
            tracker.trackEvent(
                ErrorTrack(
                    TrackApiSteps.ASSOCIATION.getType(),
                    throwable.localizedMessage.orEmpty()
                )
            )
            webUiStateMutableLiveData.postValue(WebUiState.WebError)
        }
    }

    private fun tokenizeAndAssociateCard(model: FinishInscriptionModel) {
        CoroutineScope(Dispatchers.Default).launch {
            lateinit var cardTokenId: String
            getCardToken(model)?.let {
                cardTokenId = it
                getCardAssociationId(cardTokenId, model)
            }?.let { cardAssociationId ->
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
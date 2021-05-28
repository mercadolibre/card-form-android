package com.mercadolibre.android.cardform.presentation.model

import com.mercadolibre.android.cardform.CardResultDto

internal sealed class StateUi {
    object UiLoading: StateUi()
}

internal open class UiError(val message: String = "", val messageResource: Int = 0, var showError: Boolean = true) : StateUi() {
    data class ConnectionError(val messageError: Int) : UiError(messageResource = messageError)
    data class TimeOut(val messageError: Int) : UiError(messageResource = messageError)
    data class UnknownError(val messageError: Int) : UiError(messageResource = messageError)
    data class BusinessError(val messageError: String) : UiError(messageError)
}

internal open class UiResult: StateUi() {
    object EmptyResult: UiResult()
    data class CardResult(val data: CardResultDto): UiResult()
}
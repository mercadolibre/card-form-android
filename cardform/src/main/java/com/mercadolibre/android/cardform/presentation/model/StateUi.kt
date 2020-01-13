package com.mercadolibre.android.cardform.presentation.model

sealed class StateUi {
    object UiLoading: StateUi()
}

open class UiError(val message: String = "", val messageResource: Int = 0, var showError: Boolean = true) : StateUi() {
    data class ConnectionError(val messageError: Int) : UiError(messageResource = messageError)
    data class TimeOut(val messageError: Int) : UiError(messageResource = messageError)
    data class UnknownError(val messageError: Int) : UiError(messageResource = messageError)
    data class BusinessError(val messageError: String) : UiError(messageError)
}

open class UiResult: StateUi() {
    object EmptyResult: UiResult()
    data class CardResult(val data: String): UiResult()
}
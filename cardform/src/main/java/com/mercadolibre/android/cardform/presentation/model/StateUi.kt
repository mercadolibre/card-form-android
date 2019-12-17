package com.mercadolibre.android.cardform.presentation.model

sealed class StateUi {
    object UiLoading: StateUi()
}

open class UiError(val message: Int, var showError: Boolean = true) : StateUi() {
    data class ConnectionError(val messageError: Int) : UiError(messageError)
    data class TimeOut(val messageError: Int) : UiError(messageError)
    data class UnknownError(val messageError: Int) : UiError(messageError)
}

open class UiResult: StateUi() {
    object EmptyResult: UiResult()
    data class CardResult(val data: String): UiResult()
}
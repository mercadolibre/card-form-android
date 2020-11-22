package com.mercadolibre.android.cardform.presentation.model

sealed class WebUiState(
    val iconFrom: Int,
    val iconTo: Int,
    val progressState: ProgressState
) {

    data class WebSuccess(val url: String, val token: ByteArray) : WebUiState(0, 0, ProgressState.SUCCESS)
    object WebProgress : WebUiState(0, 0, ProgressState.PROGRESS)
    object WebError : WebUiState(0, 0, ProgressState.ERROR)

    enum class ProgressState(
        val title: Int,
        val description: Int
    ) {
        PROGRESS(0, 0),
        SUCCESS(0, 0),
        ERROR(0, 0)
    }
}
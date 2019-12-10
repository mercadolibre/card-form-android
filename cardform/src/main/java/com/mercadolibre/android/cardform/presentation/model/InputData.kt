package com.mercadolibre.android.cardform.presentation.model

interface InputData {
    val name: String
    val maxLength: Int
    val type: String
    val title: String
    val hintMessage: String?
    val validationPattern: String?
    val validationMessage: String
    val mask: String?
}
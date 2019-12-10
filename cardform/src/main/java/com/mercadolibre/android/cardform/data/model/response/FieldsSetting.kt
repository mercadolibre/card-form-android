package com.mercadolibre.android.cardform.data.model.response

data class FieldsSetting (
    val name: String,
    val length : Int?,
    val type : String,
    val title : String,
    val hintMessage : String,
    val validationPattern : String,
    val validationMessage : String,
    val mask: String
)
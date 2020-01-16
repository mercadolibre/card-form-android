package com.mercadolibre.android.cardform.data.model.response

internal data class IdentificationTypes (
    val id : String,
    val name : String,
    val type : String,
    val mask : String?,
    val minLength : Int,
    val maxLength : Int
)
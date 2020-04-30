package com.mercadolibre.android.cardform.presentation.helpers

import com.mercadolibre.android.cardform.presentation.model.ValidationType

typealias Valid = () -> Unit
typealias Invalid = (errorMessage: String) -> Unit

internal object ValidationHelper {

    private val validations = mutableListOf<ValidationComponent>()

    fun validateWith(
        validationType: ValidationType,
        blockValid: Valid? = null,
        blockInvalid: Invalid? = null
    ): ValidationHelper {
        validations.add(ValidationComponent(validationType, blockValid, blockInvalid))
        return this
    }

    fun validate(): Boolean {
        validations.forEach {
            val result = it.validationType.validate()

            if (result) {
                it.blockValid?.invoke()
            } else {
                it.blockInvalid?.invoke(it.validationType.getErrorMessage())
                validations.clear()
                return result
            }
        }

        validations.clear()

        return true
    }

    internal data class ValidationComponent(
        val validationType: ValidationType,
        val blockValid: Valid? = null,
        val blockInvalid: Invalid? = null
    )
}
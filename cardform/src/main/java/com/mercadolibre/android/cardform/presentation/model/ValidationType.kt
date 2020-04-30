package com.mercadolibre.android.cardform.presentation.model

import com.mercadolibre.android.cardform.data.model.response.Validation
import com.mercadolibre.android.cardform.presentation.ui.custom.Luhn.isValid

internal sealed class ValidationType {

    abstract fun validate(): Boolean
    open fun getErrorMessage(): String = ""

    data class Complete(var input: String, var length: Int) : ValidationType() {
        override fun validate() = input.trim().length == length
    }

    data class Luhn(var input: String, var hasLuhnValidation: Boolean) : ValidationType() {
        override fun validate() = if (hasLuhnValidation) isValid(input) else !hasLuhnValidation
    }

    class ExtraValidation(var input: String, var extraValidation: List<Validation>) :
        ValidationType() {
        private var message = ""

        override fun validate(): Boolean {
            var result = true
            extraValidation.forEach {

                result = DigitValidation
                    .fromType(it.name)
                    .validate(input.replace("\\s+".toRegex(), ""), it.value[0])

                if (!result) {
                    message = it.errorMessage
                    return result
                }

            }
            return result
        }

        override fun getErrorMessage() = message
    }
}
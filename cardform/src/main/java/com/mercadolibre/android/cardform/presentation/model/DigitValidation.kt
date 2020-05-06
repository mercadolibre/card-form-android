package com.mercadolibre.android.cardform.presentation.model

enum class DigitValidation {

    SEVENTH_DIGIT {
        override val digit = 7
        override fun validate(cardNumber: String, values: List<String>): Boolean {
            val length = cardNumber.length

            return length < digit || length >= digit && validateValues(
                cardNumber[digit - 1].toString(), values
            )
        }
    },
    NONE {
        override val digit = 0
        override fun validate(cardNumber: String, values: List<String>) = true
    };

    abstract val digit: Int
    abstract fun validate(cardNumber: String, values: List<String>): Boolean
    protected open fun validateValues(value: String, values: List<String>) =
        values.contains(value)

    companion object {

        fun fromType(name: String?): DigitValidation {
            if (name == null) {
                return NONE
            }
            values().forEach {
                if (it.name.equals(name, ignoreCase = true)) {
                    return it
                }
            }

            return NONE
        }
    }
}
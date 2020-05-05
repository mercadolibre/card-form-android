package com.mercadolibre.android.cardform.presentation.model

enum class DigitValidation(private val type: String) {

    SEVENTH_DIGIT("seventh_digit") {
        override val digit = 7
        override fun validate(cardNumber: String, value: Char): Boolean {
            val length = cardNumber.length
            return length < digit || length >= digit && cardNumber[digit - 1] == value
        }
    },
    NONE("none") {
        override val digit = 0
        override fun validate(cardNumber: String, value: Char) = true
    };

    abstract val digit: Int
    abstract fun validate(cardNumber: String, value: Char): Boolean

    fun getType() = type

    companion object {

        fun fromType(type: String?): DigitValidation {
            if (type == null) {
                return NONE
            }
            values().forEach {
                if (it.getType() == type) {
                    return it
                }
            }

            return NONE
        }
    }
}
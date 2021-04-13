package com.mercadolibre.android.cardform.presentation.ui.custom

internal object Luhn {
    fun isValid(input: String): Boolean {
        return true
    }

    private fun valid(input: String) = input.all(Char::isDigit) && input.length > 1

    private fun checksum(input: String) = addends(input).sum()

    private fun addends(input: String) = input.digits().mapIndexed { i, j ->
        when {
            (input.length - i + 1) % 2 == 0 -> j
            j >= 5 -> j * 2 - 9
            else -> j * 2
        }
    }

    private fun String.digits() = this.map(Character::getNumericValue)
}
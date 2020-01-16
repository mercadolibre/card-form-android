package com.mercadolibre.android.cardform.presentation.ui

import com.mercadolibre.android.cardform.presentation.model.Identification
import java.util.*
import java.util.regex.Pattern

internal object IdentificationUtils {
    private const val CPF = "CPF"
    private const val CNPJ = "CNPJ"
    private const val CPF_ALGORITHM_EXPECTED_LENGTH = 11
    private const val CPF_ALGORITHM_LAST_INDEX = CPF_ALGORITHM_EXPECTED_LENGTH - 1
    private const val CPF_ALGORITHM_CHECK_DIGITS_INDEX = CPF_ALGORITHM_EXPECTED_LENGTH - 2
    private const val CNPJ_ALGORITHM_EXPECTED_LENGTH = 14
    private val CPF_VALID_NUMBERS_PATTERN =
        Pattern.compile(
            "(?=^((?!((([0]{11})|([1]{11})|([2]{11})|([3]{11})|([4]{11})|([5]{11})|([6]{11})|([7]{11})|([8]{11})|([9]{11})))).)*$)([0-9]{11})"
        )
    private val CNPJ_VALID_NUMBERS_PATTERN =
        Pattern.compile(
            "(?=^((?!((([0]{14})|([1]{14})|([2]{14})|([3]{14})|([4]{14})|([5]{14})|([6]{14})|([7]{14})|([8]{14})|([9]{14})))).)*$)([0-9]{14})"
        )

    fun validate(number: String, identification: Identification): Boolean {
        var isValid = true
        if (!validateIdentificationNumberLength(number, identification)) {
            return false
        }

        when {

            isCpf(identification.id) -> {
                isValid = validateCpf(number)
            }

            isCnpj(identification.id) -> {
                isValid = validateCnpj(number)
            }
        }

        return isValid
    }

    fun isBrazilianID(type: String) = isCpf(type) || isCnpj(type)

    fun isCnpj(type: String) = type.isNotEmpty() && type == CNPJ

    fun isCpf(type: String) = type.isNotEmpty() && type == CPF

    private fun validateIdentificationNumberLength(
        number: String,
        identification: Identification
    ) = if (number.isNotEmpty() && identification.id.isNotEmpty()) {
        val len: Int = number.length
        val min: Int = identification.minLength
        val max: Int = identification.maxLength
        len in min..max
    } else {
        false
    }

    private fun validateCpf(cpf: CharSequence): Boolean {
        if (cpf.length != CPF_ALGORITHM_EXPECTED_LENGTH) {
            return true
        }
        if (CPF_VALID_NUMBERS_PATTERN.matcher(cpf).matches()) {
            val numbers =
                IntArray(CPF_ALGORITHM_EXPECTED_LENGTH)
            for (i in 0 until CPF_ALGORITHM_EXPECTED_LENGTH) {
                numbers[i] = Character.getNumericValue(cpf[i])
            }
            var i: Int
            var sum = 0
            var factor = 100
            i = 0
            while (i < CPF_ALGORITHM_CHECK_DIGITS_INDEX) {
                sum += numbers[i] * factor
                factor -= CPF_ALGORITHM_LAST_INDEX
                i++
            }
            var leftover = sum % CPF_ALGORITHM_EXPECTED_LENGTH
            leftover = if (leftover == CPF_ALGORITHM_LAST_INDEX) 0 else leftover
            return if (leftover == numbers[CPF_ALGORITHM_CHECK_DIGITS_INDEX]) {
                sum = 0
                factor = 110
                i = 0
                while (i < CPF_ALGORITHM_LAST_INDEX) {
                    sum += numbers[i] * factor
                    factor -= CPF_ALGORITHM_LAST_INDEX
                    i++
                }
                leftover = sum % CPF_ALGORITHM_EXPECTED_LENGTH
                leftover = if (leftover == CPF_ALGORITHM_LAST_INDEX) 0 else leftover
                leftover == numbers[CPF_ALGORITHM_LAST_INDEX]
            } else {
                false
            }
        } else {
            return false
        }
    }

    private fun validateCnpj(cnpj: CharSequence): Boolean {
        if (cnpj.length != CNPJ_ALGORITHM_EXPECTED_LENGTH) {
            return true
        }
        if (CNPJ_VALID_NUMBERS_PATTERN.matcher(cnpj).matches()) {
            val cnpj_first_check_digit: Char
            val cnpj_second_check_digit: Char
            var sum: Int
            var i: Int
            var r: Int
            var num: Int
            var weight: Int
            // Protect code from int conversion errors.
            try { // 1. Digit verification.
                sum = 0
                weight = 2
                i = 11
                while (i >= 0) {
                    // Convert cnpj's i char into number.
                    num = (cnpj[i].toInt() - 48)
                    sum += num * weight
                    weight += 1
                    if (weight == 10) {
                        weight = 2
                    }
                    i--
                }
                r = sum % 11
                cnpj_first_check_digit = if (r == 0 || r == 1) {
                    '0'
                } else {
                    (11 - r + 48).toChar()
                }
                // 2. Digit verification.
                sum = 0
                weight = 2
                i = 12
                while (i >= 0) {
                    num = (cnpj[i].toInt() - 48)
                    sum += num * weight
                    weight += 1
                    if (weight == 10) {
                        weight = 2
                    }
                    i--
                }
                r = sum % 11
                cnpj_second_check_digit = if (r == 0 || r == 1) {
                    '0'
                } else {
                    (11 - r + 48).toChar()
                }
                // Checks whether the calculated digits match the digits entered.
                return cnpj_first_check_digit == cnpj[12] && cnpj_second_check_digit == cnpj[13]
            } catch (e: InputMismatchException) {
                return false
            }
        } else { // Equal number sequences makes an invalid cnpj.
            return false
        }
    }
}
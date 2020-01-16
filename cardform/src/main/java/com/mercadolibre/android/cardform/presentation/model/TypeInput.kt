package com.mercadolibre.android.cardform.presentation.model

import android.text.InputType
import java.lang.IllegalArgumentException

internal enum class TypeInput(private val type: String) {

    TEXT("text") {
        override fun getInputType(): Int {
            return InputType.TYPE_TEXT_VARIATION_FILTER or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or
                    InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        }

    },
    STRING("string") {
        override fun getInputType(): Int {
            return InputType.TYPE_TEXT_VARIATION_FILTER or
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or
                    InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        }

    },
    NUMBER("number") {
        override fun getInputType(): Int {
            return InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
    };

    abstract fun getInputType(): Int

    fun getType() = type

    companion object {
        fun fromType(type: String): TypeInput {
            for (b in values()) {
                if (b.type == type) {
                    return b
                }
            }
            throw IllegalArgumentException("$type is not valid argument")
        }
    }
}
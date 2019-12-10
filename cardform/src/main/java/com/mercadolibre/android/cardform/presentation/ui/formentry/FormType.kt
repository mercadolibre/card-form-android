package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.support.v4.app.Fragment
import java.lang.IllegalArgumentException

enum class FormType(private val type: String) {

    CARD_NUMBER("card_number") {
        override var exclude = false
        override fun getFragment(): Fragment {
            return CardNumberFragment()
        }
    },
    CARD_NAME("name") {
        override var exclude = true
        override fun getFragment(): Fragment {
            return CardNameFragment()
        }
    },
    CARD_SECURITY("card_security") {
        override var exclude = false
        override fun getFragment(): Fragment {
            return SecurityFragment()
        }
    },
    CARD_IDENTIFICATION("identification_types") {
        override var exclude = true
        override fun getFragment(): Fragment {
            return IdentificationFragment()
        }
    };

    abstract var exclude: Boolean
    abstract fun getFragment(): Fragment
    fun getType(): String {
        return type
    }

    companion object {

        const val EXPIRATION_TYPE = "expiration"
        const val SECURITY_CODE_TYPE = "security_code"
        private val additionalSteps = mutableListOf<String>()

        private fun fromType(type: String): FormType {
            for (b in values()) {
                if (b.type == type) {
                    return b
                }
            }
            throw IllegalArgumentException("$type is not valid argument")
        }

        fun reset() {
            additionalSteps.forEach {
                fromType(it).exclude = true
            }
            additionalSteps.clear()
        }

        fun setAdditionalSteps(steps: List<String>) {
            additionalSteps.clear()
            additionalSteps.addAll(steps)
            additionalSteps.forEach {
                val formType = fromType(it)
                if (formType.exclude) {
                    formType.exclude = false
                }
            }
        }

        fun getValue(position: Int): FormType {
            return getValues()[position]
        }

        fun getValues(): Array<FormType> {
            return values().filter { !it.exclude }.toTypedArray()
        }
    }
}
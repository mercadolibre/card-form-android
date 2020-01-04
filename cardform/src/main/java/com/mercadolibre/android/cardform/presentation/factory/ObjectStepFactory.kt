package com.mercadolibre.android.cardform.presentation.factory

import android.content.res.Resources
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.model.StepData
import com.mercadolibre.android.cardform.presentation.model.TypeInput
import com.mercadolibre.android.cardform.presentation.ui.formentry.FormType
import java.lang.StringBuilder

object ObjectStepFactory : StepFactory {
    override fun createDefaultStepFrom(
        resources: Resources,
        name: String,
        cardPattern: IntArray?
    ): StepData {
        return when (name) {
            FormType.CARD_NUMBER.getType() -> {
                val pattern = cardPattern ?: arrayOf(4,4,4,4).toIntArray()
                return StepData(
                    "card_number",
                    pattern.sum(),
                    TypeInput.NUMBER.getType(),
                    resources.getString(R.string.cf_card_number_hint),
                    "",
                    getCardRegexPattern(
                        pattern
                    ),
                    resources.getString(R.string.cf_card_number_info_hint),
                    pattern.joinToString(" ") {
                        val builder = StringBuilder()
                        for (i in 1..it) {
                            builder.append('$')
                        }
                        builder
                    }
                )
            }

            FormType.CARD_NAME.getType() -> {
                StepData(
                    "name",
                    40,
                    TypeInput.TEXT.getType(),
                    resources.getString(R.string.cf_card_name_hint),
                    "",
                    null,
                    "",
                    null
                )
            }

            FormType.EXPIRATION_TYPE -> {
                StepData(
                    "expiration",
                    0,
                    TypeInput.NUMBER.getType(),
                    "",
                    "",
                    null,
                    "",
                    "$$/$$"
                )
            }

            else -> {
                StepData(
                    "security_code",
                    0,
                    TypeInput.NUMBER.getType(),
                    "",
                    "",
                    null,
                    "",
                    null
                )
            }
        }
    }

    private fun getCardRegexPattern(cardPattern: IntArray): String {
        val builder = StringBuilder()
        cardPattern.joinToString {
            builder.append("[0-9]{$it}\\ ")
        }
        return builder.toString().trimEnd().trimEnd('\\')
    }
}
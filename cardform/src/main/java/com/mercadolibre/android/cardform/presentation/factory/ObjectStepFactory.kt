package com.mercadolibre.android.cardform.presentation.factory

import android.content.res.Resources
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.model.StepData
import com.mercadolibre.android.cardform.presentation.model.TypeInput
import com.mercadolibre.android.cardform.presentation.ui.formentry.FormType
import java.lang.StringBuilder

internal object ObjectStepFactory : StepFactory {
    override fun createDefaultStepFrom(
        resources: Resources,
        name: String,
        maxLength: Int,
        cardPattern: IntArray?
    ): StepData {
        return if (name == FormType.CARD_NUMBER.getType()) {
            val pattern = cardPattern ?: arrayOf(4, 4, 4, 4).toIntArray()
            return StepData(
                "card_number",
                if (maxLength > 0) maxLength else pattern.sum(),
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
                },
                false
            )
        } else {
            StepData(
                "name",
                40,
                TypeInput.TEXT.getType(),
                resources.getString(R.string.cf_card_name_hint),
                "",
                null,
                "",
                null,
                false
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
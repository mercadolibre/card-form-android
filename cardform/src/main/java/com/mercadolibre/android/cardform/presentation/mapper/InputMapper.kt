package com.mercadolibre.android.cardform.presentation.mapper


import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import com.mercadolibre.android.cardform.presentation.model.StepData

internal object InputMapper : Mapper<StepData, FieldsSetting> {
    override fun map(model: FieldsSetting): StepData {
        return StepData(
            model.name,
            model.length ?: 0,
            model.type,
            model.title,
            model.hintMessage,
            model.validationPattern,
            model.validationMessage,
            model.mask,
            model.autocomplete ?: true
        )
    }
}

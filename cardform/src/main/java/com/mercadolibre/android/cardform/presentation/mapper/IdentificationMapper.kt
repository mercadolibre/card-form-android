package com.mercadolibre.android.cardform.presentation.mapper

import com.mercadolibre.android.cardform.base.Mapper
import com.mercadolibre.android.cardform.data.model.response.FieldsSetting
import com.mercadolibre.android.cardform.data.model.response.IdentificationTypes
import com.mercadolibre.android.cardform.presentation.model.Identification
import com.mercadolibre.android.cardform.presentation.model.IdentificationData

internal class IdentificationMapper(private val fieldsSetting: FieldsSetting) :
    Mapper<IdentificationData, List<IdentificationTypes>> {

    override fun map(model: List<IdentificationTypes>): IdentificationData {
        return fieldsSetting.run {
            IdentificationData(name = name,
                type = type,
                title = title,
                validationMessage = validationMessage,
                identifications = model.map {
                    Identification(it.id, it.name, it.type, it.minLength, it.maxLength, it.mask)
                }
            )
        }
    }
}
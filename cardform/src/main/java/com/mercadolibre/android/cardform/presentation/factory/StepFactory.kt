package com.mercadolibre.android.cardform.presentation.factory

import android.content.res.Resources
import com.mercadolibre.android.cardform.presentation.model.StepData

interface StepFactory {
    fun createDefaultStepFrom(resources: Resources, name: String, maxLength: Int = 0, cardPattern: IntArray? = null) : StepData
}
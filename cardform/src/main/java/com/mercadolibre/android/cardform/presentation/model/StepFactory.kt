package com.mercadolibre.android.cardform.presentation.model

import android.content.res.Resources

interface StepFactory {
    fun createDefaultStepFrom(resources: Resources, name: String, cardPattern: IntArray? = null) : StepData
}
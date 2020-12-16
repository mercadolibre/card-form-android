package com.mercadolibre.android.cardform.presentation.utils

import android.graphics.Color
import androidx.annotation.ColorInt

internal object ViewUtils {
    private const val DARKEN_FACTOR = 0.1f

    @ColorInt
    fun getDarkPrimaryColor(@ColorInt primaryColor: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(primaryColor, hsv)
        hsv[1] = hsv[1] + DARKEN_FACTOR
        hsv[2] = hsv[2] - DARKEN_FACTOR
        return Color.HSVToColor(hsv)
    }
}
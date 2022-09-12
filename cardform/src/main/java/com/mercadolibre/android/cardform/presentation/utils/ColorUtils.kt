package com.mercadolibre.android.cardform.presentation.utils

import android.graphics.Color

object ColorUtils {

    @JvmStatic
    fun safeParseColor(color: String?, defaultColor: Int) = runCatching {
        Color.parseColor(color)
    }.getOrDefault(defaultColor)
}

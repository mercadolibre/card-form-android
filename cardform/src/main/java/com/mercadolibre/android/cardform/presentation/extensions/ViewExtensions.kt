package com.mercadolibre.android.cardform.presentation.extensions

import android.content.Context
import android.util.TypedValue
import android.view.View

fun <T : View> T.gone() {
    visibility = View.GONE
}

fun <T : View> T.visible() {
    visibility = View.VISIBLE
}

fun <T : View> T.invisible() {
    visibility = View.INVISIBLE
}

fun <T : View> T.getStringOrEmpty(id: Int): String = resources?.getString(id) ?: ""

fun Context.getPxFromDp(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

fun Context.getPxFromSp(sp: Float): Int {
    return TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}
package com.mercadolibre.android.cardform.presentation.extensions

import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt

internal fun <T : View> T.gone() {
    visibility = View.GONE
}

internal fun <T : View> T.visible() {
    visibility = View.VISIBLE
}

internal fun <T : View> T.invisible() {
    visibility = View.INVISIBLE
}

internal fun <T : View> T.showKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

internal fun <T : View> T.getStringOrEmpty(id: Int): String = resources?.getString(id) ?: ""

internal fun Window.changeStatusBarColor(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}


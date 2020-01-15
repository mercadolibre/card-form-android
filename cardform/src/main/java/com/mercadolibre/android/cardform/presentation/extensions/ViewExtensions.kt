package com.mercadolibre.android.cardform.presentation.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun <T : View> T.gone() {
    visibility = View.GONE
}

fun <T : View> T.visible() {
    visibility = View.VISIBLE
}

fun <T : View> T.invisible() {
    visibility = View.INVISIBLE
}

fun <T : View> T.showKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun <T : View> T.getStringOrEmpty(id: Int): String = resources?.getString(id) ?: ""
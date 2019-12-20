package com.mercadolibre.android.cardform.presentation.extensions

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
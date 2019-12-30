package com.mercadolibre.android.cardform.presentation.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Fragment.showKeyboard() {
    activity?.showKeyboard()
}

fun Activity.showKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun Fragment.addKeyBoardListener(
    onKeyBoardOpen: (() -> Unit)? = null,
    onKeyBoardClose: (() -> Unit)? = null
) {

    view?.apply {
        viewTreeObserver?.addOnGlobalLayoutListener {
            val r = Rect()

            getWindowVisibleDisplayFrame(r)

            val heightDiff = rootView.height - (r.bottom - r.top)
            if (heightDiff > rootView.height * 0.15) {
                onKeyBoardOpen?.invoke()
            } else {
                onKeyBoardClose?.invoke()
            }
        }
    }
}
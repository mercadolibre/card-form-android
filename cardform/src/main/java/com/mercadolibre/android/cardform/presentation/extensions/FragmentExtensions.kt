package com.mercadolibre.android.cardform.presentation.extensions


import android.app.Activity
import android.content.Context
import android.graphics.Rect
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

internal fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

internal fun Fragment.showKeyboard() {
    activity?.showKeyboard()
}

internal fun Activity.showKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

internal fun FragmentManager.setupForAccessibility() {
    addOnBackStackChangedListener {
        val lastFragmentWithView = fragments.last { it.view != null }
        for (fragment in fragments) {
            if (fragment == lastFragmentWithView) {
                fragment?.view?.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
            } else {
                fragment?.view?.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
            }
        }
    }
}

internal fun FragmentActivity?.setupForAccessibility() {
    this?.supportFragmentManager?.setupForAccessibility()
}

internal fun Fragment?.setupForAccessibility() {
    this?.activity?.setupForAccessibility()
}

internal fun Fragment.addKeyBoardListener(
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
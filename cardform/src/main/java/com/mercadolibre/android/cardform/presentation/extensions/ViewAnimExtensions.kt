package com.mercadolibre.android.cardform.presentation.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.support.annotation.AnimRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import android.widget.TextView
import com.mercadolibre.android.cardform.R


private fun loadAnimation(context: Context, @AnimRes id: Int): Animation {
    return AnimationUtils.loadAnimation(context, id)
}

private fun <T : View> T.getAnimationListener(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
): Animation.AnimationListener {
    return object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
            onRepeat?.invoke()
        }

        override fun onAnimationEnd(animation: Animation?) {
            onFinish?.invoke()
        }

        override fun onAnimationStart(animation: Animation?) {
            onStart?.invoke()
        }
    }
}

fun <T : View> T.pushUpIn(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.push_up_in
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.pushDownIn(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.push_down_in
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.pushDownOut(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.push_down_out
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.slideInRight(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.slide_in_right
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.fadeIn(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.fade_in
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.fadeOut(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.fade_out
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.slideOutLeft(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.slide_out_left
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.slideOutRight(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.slide_out_right
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun <T : View> T.slideInLeft(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.slide_in_left
    ).apply {
        setAnimationListener(
            getAnimationListener(
                onRepeat,
                onFinish,
                onStart
            )
        )
        startAnimation(this)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard() }
}

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(HIDE_IMPLICIT_ONLY, 0)
}

fun Fragment.showKeyboard() {
    view?.let { activity?.showKeyboard() }
}

fun Activity.showKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(SHOW_IMPLICIT, 0)
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
package com.mercadolibre.android.cardform.presentation.extensions

import android.view.animation.Animation

typealias AnimationListener = () -> Unit

internal fun Animation.setAnimationListener(repeat: AnimationListener? = null,
    start: AnimationListener? = null, finish: AnimationListener? = null) {
    setAnimationListener(object : Animation.AnimationListener {

        override fun onAnimationRepeat(animation: Animation?) {
            repeat?.invoke()
        }

        override fun onAnimationEnd(animation: Animation?) {
            finish?.invoke()
        }

        override fun onAnimationStart(animation: Animation?) {
            start?.invoke()
        }
    })
}
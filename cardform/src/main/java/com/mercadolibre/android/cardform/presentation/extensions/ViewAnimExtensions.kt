package com.mercadolibre.android.cardform.presentation.extensions

import android.content.Context
import android.support.annotation.AnimRes
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.mercadolibre.android.cardform.R


private fun loadAnimation(context: Context, @AnimRes id: Int): Animation {
    return AnimationUtils.loadAnimation(context, id)
}

fun <T : View> T.pushUpIn(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(it.applicationContext, R.anim.push_up_in).apply {
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
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
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}
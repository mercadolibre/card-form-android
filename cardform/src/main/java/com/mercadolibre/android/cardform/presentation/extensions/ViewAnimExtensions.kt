package com.mercadolibre.android.cardform.presentation.extensions

import android.content.Context
import android.support.annotation.AnimRes
import android.support.v4.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.mercadolibre.android.cardform.R

private fun loadAnimation(context: Context, @AnimRes id: Int): Animation {
    return AnimationUtils.loadAnimation(context, id)
}

internal fun <T : View> T.pushUpIn(
    startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(
        context.applicationContext,
        R.anim.cf_push_up_in
    ).apply {
        startOffset?.let {
            setStartOffset(it)
        }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun Fragment.postDelayed(delay: Long, runnable: (() -> Unit)) = view?.postDelayed(runnable, delay)

internal fun <T : View> T.goneDuringAnimation() = context?.let {
    loadAnimation(it, R.anim.cf_gone).apply {
        startAnimation(this)
    }
}

internal fun <T : View> T.pushDownIn(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.cf_push_down_in
    ).apply {
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.pushDownOut(
    startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(
        context,
        R.anim.cf_push_down_out
    ).apply {
        startOffset?.let { setStartOffset(it) }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.slideLeftIn(
    startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(
        context.applicationContext,
        R.anim.cf_slide_left_in
    ).apply {
        startOffset?.let {
            setStartOffset(it)
        }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.fadeOut(duration: Long? = null, startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null, onFinish: (() -> Unit)? = null, onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(context, R.anim.cf_fade_out).apply {
        duration?.let { setDuration(it) }
        startOffset?.let { setStartOffset(it) }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.fadeIn(duration: Long? = null, startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null, onFinish: (() -> Unit)? = null, onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(context, R.anim.cf_fade_in).apply {
        duration?.let { setDuration(it) }
        startOffset?.let { setStartOffset(it) }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.slideLeftOut(
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {
    loadAnimation(
        it.applicationContext,
        R.anim.cf_slide_left_out
    ).apply {
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.slideRightOut(
    startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(
        context,
        R.anim.cf_slide_right_out
    ).apply {
        startOffset?.let { setStartOffset(it) }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}

internal fun <T : View> T.slideRightIn(
    startOffset: Long? = null,
    onRepeat: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null
) = context?.let {context ->
    loadAnimation(
        context,
        R.anim.cf_slide_right_in
    ).apply {
        startOffset?.let { setStartOffset(it) }
        setAnimationListener(onRepeat, onStart, onFinish)
        startAnimation(this)
    }
}
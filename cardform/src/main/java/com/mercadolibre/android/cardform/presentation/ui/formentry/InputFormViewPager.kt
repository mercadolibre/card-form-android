package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.mercadolibre.android.cardform.presentation.extensions.getPxFromDp
import kotlin.math.min


internal class InputFormViewPager : ViewPager {

    private var scrollEnable = true

    constructor(context: Context) : super(context) {
        initInputFormViewPager()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initInputFormViewPager()
    }

    private fun initInputFormViewPager() {
        clipToPadding = false
        setPadding( context.getPxFromDp(52f).toInt(), 0,  context.getPxFromDp(52f).toInt(), 0)
        pageMargin = context.getPxFromDp(20f).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasure = heightMeasureSpec
        var height = 0
        var view: View? = null
        for (i in 0 until childCount) {
            view = getChildAt(i)
            view.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val h = view.measuredHeight
            if (h > height) height = h
        }

        if (height != 0) {
            heightMeasure = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasure)

        setMeasuredDimension(measuredWidth, measureHeight(heightMeasure, view))
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view the base view with already measured height
     *
     * @return The height of the view, honoring constraints from measureSpec
     */
    private fun measureHeight(measureSpec: Int, view: View?): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            if (view != null) {
                result = measuredHeight
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (scrollEnable)
            return super.onTouchEvent(ev)

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (scrollEnable)
            return super.onInterceptTouchEvent(ev)

        return false
    }

    fun setScrollEnable(enable: Boolean) {
        scrollEnable = enable
    }
}
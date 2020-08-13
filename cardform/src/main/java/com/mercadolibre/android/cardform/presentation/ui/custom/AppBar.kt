package com.mercadolibre.android.cardform.presentation.ui.custom

import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mercadolibre.android.cardform.R
import kotlinx.android.synthetic.main.app_bar.view.*

internal class AppBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {

    init {
        configureView()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private fun configureView() {
        orientation = VERTICAL
        inflate(context, R.layout.app_bar, this)
    }

    fun setTitle(value: CharSequence) {
        title.text = value
    }

    fun setTitle(value: Int) {
        title.text = context.resources.getString(value)
    }

    fun updateProgress(value: Int) {
        with(progress) {
            ObjectAnimator.ofInt(this, "progress", progress, progress + value).start()
        }
    }

    fun configureToolbar(activity: AppCompatActivity) {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    fun setOnBackListener(listener: (v: View) -> Unit) {
        toolbar.setNavigationOnClickListener(listener)
    }
}
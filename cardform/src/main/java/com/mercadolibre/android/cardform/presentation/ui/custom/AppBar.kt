package com.mercadolibre.android.cardform.presentation.ui.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.mercadolibre.android.cardform.R
import kotlinx.android.synthetic.main.app_bar.view.*

class AppBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        configureView()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private fun configureView() {
        inflate(context, R.layout.app_bar, this)
    }

    fun setTitle(value: CharSequence) {
        title.text = value
    }

    fun setTitle(value: Int) {
        title.text = context.resources.getString(value)
    }

    fun setOnBackListener(listener: (v: View) -> Unit) {
        backButton.setOnClickListener(listener)
    }

    fun updateProgress(value: Int) {
        progress.progress += value
    }
}
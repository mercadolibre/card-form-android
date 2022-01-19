package com.mercadolibre.android.cardform.presentation.ui.custom

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.databinding.AppBarBinding

internal class AppBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var appBarBinding: AppBarBinding

    init {
        configureView()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private fun configureView() {
        orientation = VERTICAL
        inflate(context, R.layout.app_bar, this)
        appBarBinding = AppBarBinding.bind(this)
    }

    fun setTitle(value: CharSequence) {
        appBarBinding.title.text = value
    }

    fun setTitle(value: Int) {
        appBarBinding.title.text = context.resources.getString(value)
    }

    fun updateProgress(value: Int) {
        with(appBarBinding.progress) {
            ObjectAnimator.ofInt(this, "progress", progress, progress + value).start()
        }
    }

    fun configureToolbar(activity: AppCompatActivity) {
        activity.setSupportActionBar(appBarBinding.toolbar)
        activity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    fun setOnBackListener(listener: (v: View) -> Unit) {
        appBarBinding.toolbar.setNavigationOnClickListener(listener)
    }
}
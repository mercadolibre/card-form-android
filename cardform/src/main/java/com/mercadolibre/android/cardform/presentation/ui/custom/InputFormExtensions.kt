package com.mercadolibre.android.cardform.presentation.ui.custom

import android.support.v7.widget.AppCompatEditText
import android.view.MotionEvent
import android.widget.EditText

fun AppCompatEditText.addRightDrawableClicked(onClicked: ((view: EditText) -> Unit)?) {
    setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked?.invoke(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}
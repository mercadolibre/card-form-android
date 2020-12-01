package com.mercadolibre.android.cardform.base

import android.text.TextUtils

internal fun <T: CharSequence> T?.orIfEmpty(default: String): String {
    return if (!TextUtils.isEmpty(this)) {
        this!!.toString()
    } else {
        default
    }
}
package com.mercadolibre.android.cardform.base

import android.text.TextUtils

internal fun String?.orIfEmpty(default: String): String {
    return if (!TextUtils.isEmpty(this)) {
        this!!
    } else {
        default
    }
}
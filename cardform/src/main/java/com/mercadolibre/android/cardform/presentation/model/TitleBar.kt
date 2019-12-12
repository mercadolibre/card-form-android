package com.mercadolibre.android.cardform.presentation.model

import android.support.annotation.StringRes
import com.mercadolibre.android.cardform.R

enum class TitleBar(private val type: String) {
    CREDIT_TITLE("credit_card") {
        override fun getTitle() = R.string.cf_credit_title_app_bar
    },
    DEBIT_TITLE("debit_card") {
        override fun getTitle() = R.string.cf_debit_title_app_bar
    },
    NONE_TITLE("new_card") {
        override fun getTitle() = R.string.cf_generic_title_app_bar
    };

    @StringRes
    abstract fun getTitle(): Int

    fun getType() = type

    companion object {
        fun fromType(type: String): TitleBar {
            values().forEach {
                if (it.getType() == type) {
                    return it
                }
            }

            return NONE_TITLE
        }
    }
}
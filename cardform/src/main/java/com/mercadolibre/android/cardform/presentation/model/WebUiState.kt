package com.mercadolibre.android.cardform.presentation.model

import android.content.Context
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.extensions.isML

sealed class WebUiState(
    val layoutState: Int,
    val title: Int,
    val description: Int,
    private val iconFrom: Int = 0,
    private val iconTo: Int = 0
) {

    open fun getIconFrom(context: Context) = iconFrom
    open fun getIconTo(context: Context) = iconTo

    object WebSuccess : WebUiState(
        R.layout.cf_web_view_success_state,
        0,
        0
    )

    object WebError : WebUiState(
        R.layout.cf_web_view_error_state,
        R.string.cf_web_view_progress_error_title,
        R.string.cf_web_view_progress_error_description
    )

    object WebProgressBack : WebUiState(
        R.layout.cf_web_view_progress_state,
        R.string.cf_web_view_progress_back_title,
        R.string.cf_web_view_progress_description,
        R.drawable.cf_web_view_mp_logo,
        R.drawable.cf_web_view_icon_web_pay
    ) {

        override fun getIconFrom(context: Context): Int {
            if (context.isML()) {
                return R.drawable.cf_web_view_ml_logo
            }
            return super.getIconFrom(context)
        }
    }

    object WebProgressStart : WebUiState(
        R.layout.cf_web_view_progress_state,
        R.string.cf_web_view_progress_start_title,
        R.string.cf_web_view_progress_description,
        R.drawable.cf_web_view_icon_web_pay,
        R.drawable.cf_web_view_mp_logo
    ) {

        override fun getIconTo(context: Context): Int {
            if (context.isML()) {
                return R.drawable.cf_web_view_ml_logo
            }
            return super.getIconTo(context)
        }
    }
}
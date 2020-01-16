package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.view.View
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.network.exceptions.CardFormException
import com.mercadolibre.android.cardform.presentation.extensions.getStringOrEmpty
import com.mercadolibre.android.cardform.presentation.model.UiError
import com.mercadolibre.android.ui.widgets.MeliSnackbar
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal object ErrorUtil {

    fun createError(e: Throwable = UnknownError()): UiError {

        return when (e) {
            is SocketTimeoutException -> {
                UiError.TimeOut(R.string.cf_generic_error)
            }

            is UnknownHostException -> {
                UiError.ConnectionError(R.string.cf_without_connection)
            }

            is IOException -> {
                UiError.UnknownError(R.string.cf_generic_error)
            }

            is CardFormException -> {
                UiError.BusinessError(e.message)
            }

            else -> {
                UiError.UnknownError(R.string.cf_generic_error)
            }
        }
    }

    @SuppressLint("Range")
    fun resolveError(rootView: View, uiError: UiError, action: View.OnClickListener? = null) {
        val message = if (uiError.messageResource != 0) {
            rootView.getStringOrEmpty(uiError.messageResource)
        } else {
            uiError.message
        }
        MeliSnackbar
            .make(
                rootView,
                message,
                Snackbar.LENGTH_LONG,
                MeliSnackbar.SnackbarType.ERROR
            ).apply { if (action != null) { setAction(R.string.cf_retry, action) } }.show()
    }
}
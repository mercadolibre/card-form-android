package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.view.View
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.action.AndesSnackbarAction
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.network.exceptions.BusinessException
import com.mercadolibre.android.cardform.network.exceptions.CardFormException
import com.mercadolibre.android.cardform.presentation.extensions.getStringOrEmpty
import com.mercadolibre.android.cardform.presentation.model.UiError
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

            is BusinessException -> {
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
        AndesSnackbar(
                rootView.context,
                rootView,
                AndesSnackbarType.ERROR,
                message,
                AndesSnackbarDuration.LONG
            ).apply { if (action != null) { AndesSnackbarAction(R.string.cf_retry.toString(), action) } }.show()
    }
}
package com.mercadolibre.android.cardform.base

internal typealias ResponseCallback<R> = Response<R, Throwable>

internal fun <T, R> ResponseCallback<T>.map(transform: (T) -> R): ResponseCallback<R> {
    return when (this) {
        is Response.Success -> {
            try {
                success(transform(result))
            } catch (e: Exception) {
                failure(e)
            }
        }
        is Response.Failure -> failure(exception)
    }
}

internal fun <T> ResponseCallback<T>.fold(
    success: (value: T) -> Unit,
    error: (error: Throwable) -> Unit
) {
    when(this) {
        is Response.Success -> success(result)
        is Response.Failure -> error(exception)
    }
}

internal fun <T> ResponseCallback<T>.getOrElse(block: (failure: Throwable)-> Unit): T? {
    val value = getOrNull()
    if (value == null) {
        block((this as Response.Failure).exception)
    }
    return value
}

internal fun <T> ResponseCallback<T>?.getOrNull(): T? {
    return when(this) {
        is Response.Success -> result
        else -> null
    }
}
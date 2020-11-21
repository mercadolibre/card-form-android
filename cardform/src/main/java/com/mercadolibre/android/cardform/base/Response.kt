package com.mercadolibre.android.cardform.base

sealed class Response<out T, out F> {
    data class Success<out T>(val result: T): Response<T, Nothing>()
    data class Failure<out F>(val exception: F): Response<Nothing, F>()

    fun <T> success(r: T) = Success(r)
    fun <F> failure(f: F) = Failure(f)
}
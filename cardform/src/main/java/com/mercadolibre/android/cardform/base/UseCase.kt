package com.mercadolibre.android.cardform.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.mercadolibre.android.cardform.base.Response.Failure

internal typealias CallBack<T> = (T) -> Unit

internal abstract class UseCase<in P, out R> {

    protected open val contextProvider = CoroutineContextProvider()
    protected abstract suspend fun doExecute(param: P): ResponseCallback<R>

    fun execute(param: P, success: CallBack<R> = {}, failure: CallBack<Throwable> = {}) {
        runCatching {
            CoroutineScope(contextProvider.Default).launch {
                doExecute(param).also { response ->
                    withContext(contextProvider.Main) { response.fold(success, failure) }
                }
            }
        }.onFailure(failure)
    }

    suspend fun execute(param: P) = runCatching {
        withContext(contextProvider.Default) {
            doExecute(param).also { response -> withContext(contextProvider.Default) { response } }
        }
    }.getOrElse(::Failure)
}
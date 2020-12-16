package com.mercadolibre.android.cardform.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.mercadolibre.android.cardform.base.Response.Failure

internal typealias CallBack<T> = (T) -> Unit

internal abstract class UseCase<in P, out R> {

    private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()
    protected abstract suspend fun doExecute(param: P): ResponseCallback<R>

    fun execute(param: P, success: CallBack<R> = {}, failure: CallBack<Throwable> = {}) =
        CoroutineScope(contextProvider.Default).launch {
            runCatching {
                doExecute(param).also { response ->
                    withContext(contextProvider.Main) { response.fold(success, failure) }
                }
            }.onFailure { withContext(contextProvider.Main) { failure(it) } }
        }

    suspend fun execute(param: P) = withContext(contextProvider.Default) {
        runCatching {
            doExecute(param).also { response -> withContext(contextProvider.Default) { response } }
        }.getOrElse { withContext(contextProvider.Main) { Failure(it) } }
    }
}
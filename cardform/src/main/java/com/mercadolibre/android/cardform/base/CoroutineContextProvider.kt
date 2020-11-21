package com.mercadolibre.android.cardform.base

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider {
    open val Main: CoroutineContext by lazy { Dispatchers.Main }
    open val Default: CoroutineContext by lazy { Dispatchers.Default }
    open val IO: CoroutineContext by lazy { Dispatchers.IO }
}
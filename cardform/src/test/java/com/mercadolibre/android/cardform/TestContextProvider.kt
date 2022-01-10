package com.mercadolibre.android.cardform

import com.mercadolibre.android.cardform.base.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestContextProvider() : CoroutineContextProvider() {
    override var Main: CoroutineContext = Dispatchers.Unconfined
    override var IO: CoroutineContext = Dispatchers.Unconfined
    override var Default: CoroutineContext = Dispatchers.Unconfined

    constructor(io: CoroutineContext, main: CoroutineContext) : this() {
        Main = main
        IO = io
    }

}
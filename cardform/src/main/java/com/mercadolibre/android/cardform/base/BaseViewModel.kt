package com.mercadolibre.android.cardform.base

import androidx.lifecycle.ViewModel
import android.os.Bundle
import com.mercadolibre.android.cardform.internal.LifecycleListener

internal abstract class BaseViewModel : ViewModel() {
    protected val contextProvider = CoroutineContextProvider()

    var lifecycleListener: LifecycleListener? = null

    open fun recoverFromBundle(bundle: Bundle) = Unit
    open fun storeInBundle(bundle: Bundle) = Unit

    override fun onCleared() {
        super.onCleared()
        lifecycleListener = null
    }
}
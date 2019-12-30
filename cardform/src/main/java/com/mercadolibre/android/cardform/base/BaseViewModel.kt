package com.mercadolibre.android.cardform.base

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.mercadolibre.android.cardform.LifecycleListener

abstract class BaseViewModel : ViewModel() {

    var lifecycleListener: LifecycleListener? = null

    open fun recoverFromBundle(bundle: Bundle) = Unit
    open fun storeInBundle(bundle: Bundle) = Unit

    override fun onCleared() {
        super.onCleared()
        lifecycleListener = null
    }
}
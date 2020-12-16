package com.mercadolibre.android.cardform.di

import android.content.ComponentCallbacks
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel


internal inline fun <reified T : ViewModel> ComponentCallbacks.viewModel(): Lazy<T> {

    val lazyViewModelModule = lazy { Dependencies.instance.viewModelModule!! }

    return when (this) {
        is FragmentActivity -> {
            lazy { lazyViewModelModule.value.get(this, T::class.java) }
        }

        is Fragment -> {
            lazy { lazyViewModelModule.value.get(this, T::class.java) }
        }

        else -> error("Component must be a Fragment or FragmentActivity")
    }
}

internal inline fun <reified T : ViewModel> sharedViewModel(
    crossinline scope: () -> ComponentCallbacks
): Lazy<T> {
    return lazy { scope().viewModel<T>().value }
}
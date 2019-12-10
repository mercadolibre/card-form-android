package com.mercadolibre.android.cardform.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

class ViewModelModule(private val fragment: Fragment, repositoryModule: RepositoryModule) {

    private val factory = ViewModelFactory(repositoryModule)

    fun <T : ViewModel?> get(modelClass: Class<T>): T {
        return ViewModelProviders.of(fragment, factory).get(modelClass)
    }
}
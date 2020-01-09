package com.mercadolibre.android.cardform.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.mercadolibre.android.cardform.data.model.esc.Device

class ViewModelModule(private val fragment: Fragment, repositoryModule: RepositoryModule,
    behaviourModule: BehaviourModule) {

    private val factory = ViewModelFactory(repositoryModule, behaviourModule, Device(fragment.context!!))

    fun <T : ViewModel?> get(modelClass: Class<T>): T {
        return ViewModelProviders.of(fragment, factory).get(modelClass)
    }
}
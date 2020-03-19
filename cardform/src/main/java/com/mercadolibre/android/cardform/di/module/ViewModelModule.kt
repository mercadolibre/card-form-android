package com.mercadolibre.android.cardform.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.mercadolibre.android.cardform.data.model.esc.Device

internal class ViewModelModule(private val fragment: Fragment, repositoryModule: RepositoryModule,
    behaviourModule: BehaviourModule, trackerModule: TrackerModule) {

    private val factory = ViewModelFactory(repositoryModule, behaviourModule, Device(fragment.context!!), trackerModule)

    fun <T : ViewModel?> get(modelClass: Class<T>): T = ViewModelProviders.of(fragment, factory).get(modelClass)
}
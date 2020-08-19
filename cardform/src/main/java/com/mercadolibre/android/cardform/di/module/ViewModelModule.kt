package com.mercadolibre.android.cardform.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.data.model.esc.Device

internal class ViewModelModule(private val fragment: Fragment, repositoryModule: RepositoryModule,
    behaviourModule: BehaviourModule, trackerModule: TrackerModule) {

    private val factory = ViewModelFactory(repositoryModule, behaviourModule, Device(fragment.context!!), trackerModule)

    fun <T : ViewModel?> get(modelClass: Class<T>): T = ViewModelProviders.of(fragment, factory).get(modelClass)
}
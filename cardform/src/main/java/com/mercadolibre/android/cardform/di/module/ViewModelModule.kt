package com.mercadolibre.android.cardform.di.module

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.service.CardFormIntent
import com.mercadolibre.android.cardform.service.CardFormRequestHandler
import com.mercadolibre.android.cardform.service.CardFormService
import com.mercadolibre.android.cardform.service.CardFormServiceManager

internal class ViewModelModule(
    context: Context,
    useCaseModule: UseCaseModule,
    repositoryModule: RepositoryModule,
    behaviourModule: BehaviourModule,
    trackerModule: TrackerModule,
    serviceModule: ServiceModule?
) {

    private val factory = ViewModelFactory(
        useCaseModule,
        repositoryModule,
        behaviourModule,
        Device(context),
        trackerModule,
        serviceModule
    )

    fun <T : ViewModel?> get(fragment: Fragment, modelClass: Class<T>): T =
        ViewModelProviders.of(fragment, factory).get(modelClass)

    fun <T : ViewModel?> get(activity: FragmentActivity, modelClass: Class<T>): T =
        ViewModelProviders.of(activity, factory).get(modelClass)
}
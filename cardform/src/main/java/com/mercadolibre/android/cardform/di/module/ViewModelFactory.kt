package com.mercadolibre.android.cardform.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mercadolibre.android.cardform.presentation.viewmodel.InputFormViewModel

class ViewModelFactory(private val repositoryModule: RepositoryModule,
                       private val trackerModule: TrackerModule) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputFormViewModel::class.java)) {
            return InputFormViewModel(
                repositoryModule.cardRepository,
                repositoryModule.tokenizeRepository,
                repositoryModule.cardAssociation,
                trackerModule.tracker
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
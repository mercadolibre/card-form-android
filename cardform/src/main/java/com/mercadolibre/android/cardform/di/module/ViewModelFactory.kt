package com.mercadolibre.android.cardform.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.presentation.viewmodel.InputFormViewModel

internal class ViewModelFactory(
    private val useCaseModule: UseCaseModule,
    private val repositoryModule: RepositoryModule,
    private val behaviourModule: BehaviourModule,
    private val device: Device,
    private val trackerModule: TrackerModule
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputFormViewModel::class.java)) {
            return InputFormViewModel(
                repositoryModule.cardRepository,
                useCaseModule.tokenizeUseCase,
                useCaseModule.cardAssociationUseCase,
                behaviourModule.escManager,
                device,
                trackerModule.tracker
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
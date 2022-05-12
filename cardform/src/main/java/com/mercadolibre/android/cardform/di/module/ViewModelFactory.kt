package com.mercadolibre.android.cardform.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.android.cardform.data.model.esc.Device
import com.mercadolibre.android.cardform.presentation.viewmodel.webview.CardFormWebViewModel
import com.mercadolibre.android.cardform.presentation.viewmodel.InputFormViewModel

internal class ViewModelFactory(
    private val useCaseModule: UseCaseModule,
    private val repositoryModule: RepositoryModule,
    private val behaviourModule: BehaviourModule,
    private val device: Device,
    private val trackerModule: TrackerModule,
    private val serviceModule: ServiceModule?
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return run {
            when {
                modelClass.isAssignableFrom(InputFormViewModel::class.java) -> {
                    InputFormViewModel(
                        repositoryModule.cardRepository,
                        useCaseModule.cardTokenUseCase,
                        useCaseModule.cardAssociationUseCase,
                        behaviourModule.escManager,
                        device,
                        trackerModule.tracker,
                        useCaseModule.tokenDeviceUseCase
                    )
                }
                modelClass.isAssignableFrom(CardFormWebViewModel::class.java) -> {
                    CardFormWebViewModel(
                        useCaseModule.inscriptionUseCase,
                        useCaseModule.finishInscriptionUseCase,
                        useCaseModule.cardAssociationUseCase,
                        trackerModule.tracker,
                        serviceModule?.cardFormServiceManager
                    )
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        } as T
    }
}

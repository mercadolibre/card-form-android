package com.mercadolibre.android.cardform.di

import android.content.Context
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.di.module.BehaviourModule
import com.mercadolibre.android.cardform.di.module.LocalRepositoryModule
import com.mercadolibre.android.cardform.di.module.NetworkModule
import com.mercadolibre.android.cardform.di.module.RepositoryModule
import com.mercadolibre.android.cardform.di.module.ServiceModule
import com.mercadolibre.android.cardform.di.module.TrackerModule
import com.mercadolibre.android.cardform.di.module.UseCaseModule
import com.mercadolibre.android.cardform.di.module.ViewModelModule

internal class Dependencies {

    var networkModule: NetworkModule? = null
        private set
    var useCaseModule: UseCaseModule? = null
        private set
    var repositoryModule: RepositoryModule? = null
        private set
    var viewModelModule: ViewModelModule? = null
        private set
    var localPreferences: LocalRepositoryModule? = null
        private set
    var behaviourModule: BehaviourModule? = null
        private set
    var serviceModule: ServiceModule? = null

    var trackerModule: TrackerModule? = null

    fun initialize(context: Context, cardForm: CardForm) {
        networkModule = NetworkModule(context, cardForm.sessionId, cardForm.flowId, cardForm.accessToken)
        behaviourModule = BehaviourModule(cardForm.sessionId)

        repositoryModule = RepositoryModule(
            networkModule!!.retrofit,
            cardForm.siteId,
            cardForm.excludedTypes,
            cardForm.flowId,
            cardForm.sessionId,
            cardForm.cardInfo,
            cardForm.acceptThirdPartyCard,
            cardForm.activateCard,
            behaviourModule!!
        )
        useCaseModule = UseCaseModule(cardForm, repositoryModule!!)
        localPreferences = LocalRepositoryModule(context.applicationContext)
        trackerModule = TrackerModule(
            cardForm.siteId,
            cardForm.flowId,
            cardForm.sessionId,
            behaviourModule!!.trackerBehaviour
        )
        serviceModule = cardForm.cardFormIntent?.let { ServiceModule(context, it) }
        viewModelModule = ViewModelModule(
            context,
            useCaseModule!!,
            repositoryModule!!,
            behaviourModule!!,
            trackerModule!!,
            serviceModule
        )
    }

    fun clean() {
        networkModule = null
        repositoryModule = null
        viewModelModule = null
        localPreferences = null
        behaviourModule = null
        trackerModule = null
        serviceModule = null
    }

    companion object {
        val instance: Dependencies by lazy { Dependencies() }
    }
}

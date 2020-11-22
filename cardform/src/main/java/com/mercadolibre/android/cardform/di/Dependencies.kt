package com.mercadolibre.android.cardform.di

import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.di.module.*

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

    var trackerModule: TrackerModule? = null

    fun initialize(fragment: Fragment, cardForm: CardForm) {
        val activity = fragment.activity!!
        networkModule = NetworkModule(activity, cardForm.sessionId)
        behaviourModule = BehaviourModule(cardForm.sessionId)
        repositoryModule = RepositoryModule(networkModule!!.retrofit, cardForm.accessToken!!,
            cardForm.siteId, cardForm.excludedTypes)
        useCaseModule = UseCaseModule(repositoryModule!!)
        localPreferences = LocalRepositoryModule(activity.applicationContext)
        trackerModule = TrackerModule(cardForm.siteId,
            cardForm.flowId,
            cardForm.sessionId,
            behaviourModule!!.trackerBehaviour)
        viewModelModule = ViewModelModule(activity, useCaseModule!!, repositoryModule!!, behaviourModule!!, trackerModule!!)
    }

    fun clean() {
        networkModule = null
        repositoryModule = null
        viewModelModule = null
        localPreferences =  null
        behaviourModule = null
        trackerModule = null
    }

    companion object {
        val instance: Dependencies by lazy { Dependencies() }
    }
}
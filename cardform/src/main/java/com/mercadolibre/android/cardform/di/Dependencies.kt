package com.mercadolibre.android.cardform.di

import android.support.v4.app.Fragment
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.di.module.*

class Dependencies {

    var networkModule: NetworkModule? = null
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
        localPreferences = LocalRepositoryModule(activity.applicationContext)
        trackerModule = TrackerModule(cardForm.siteId,
            cardForm.flowId,
            cardForm.sessionId,
            behaviourModule!!.trackerBehaviour)
        viewModelModule = ViewModelModule(fragment, repositoryModule!!, behaviourModule!!, trackerModule!!)
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
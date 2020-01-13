package com.mercadolibre.android.cardform.di

import android.support.v4.app.Fragment
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.di.module.*
import java.util.*

class Dependencies {

    var networkModule: NetworkModule? = null
        private set
    var repositoryModule: RepositoryModule? = null
        private set
    var viewModelModule: ViewModelModule? = null
        private set
    var localPreferences: LocalRepositoryModule? = null
        private set

    var trackerModule: TrackerModule? = null

    fun initialize(fragment: Fragment, cardForm: CardForm) {
        networkModule = NetworkModule(fragment.activity!!, "12312312")
        repositoryModule = RepositoryModule(networkModule!!.retrofit, cardForm.accessToken!!,
            cardForm.siteId, cardForm.excludedTypes)
        localPreferences = LocalRepositoryModule(fragment.activity!!.applicationContext)
        trackerModule = TrackerModule(cardForm.siteId,
            "test_flow",
            "12312312")
        viewModelModule = ViewModelModule(fragment, repositoryModule!!, trackerModule!!)
    }

    fun clean() {
        networkModule = null
        repositoryModule = null
        viewModelModule = null
        localPreferences =  null
        trackerModule = null
    }

    companion object {
        val instance: Dependencies by lazy { Dependencies() }
    }
}
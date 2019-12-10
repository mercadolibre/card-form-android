package com.mercadolibre.android.cardform.di

import android.support.v4.app.Fragment
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.di.module.*
import com.mercadolibre.android.cardform.di.module.NetworkModule
import com.mercadolibre.android.cardform.di.module.RepositoryModule
import com.mercadolibre.android.cardform.di.module.ViewModelModule

class Dependencies {

    var networkModule: NetworkModule? = null
        private set
    var repositoryModule: RepositoryModule? = null
        private set
    var viewModelModule: ViewModelModule? = null
        private set
    var localPreferences: LocalRepositoryModule? = null
        private set

    fun initialize(fragment: Fragment, cardForm: CardForm) {
        networkModule = NetworkModule(fragment.activity!!)
        repositoryModule = RepositoryModule(networkModule!!.retrofit, cardForm.accessToken!!,
            cardForm.siteId, cardForm.excludedTypes)
        viewModelModule = ViewModelModule(fragment, repositoryModule!!)
        localPreferences = LocalRepositoryModule(fragment.activity!!.applicationContext)
    }

    fun clean() {
        networkModule = null
        repositoryModule = null
        viewModelModule = null
        localPreferences =  null
    }

    companion object {
        val instance: Dependencies by lazy { Dependencies() }
    }
}
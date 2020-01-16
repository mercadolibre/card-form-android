package com.mercadolibre.android.cardform.di.module

import android.content.Context
import android.content.SharedPreferences
import com.mercadolibre.android.cardform.di.preferences.IdentificationPreferences
import com.mercadolibre.android.cardform.di.preferences.NameOwnerPreferences

internal class LocalRepositoryModule(applicationContext: Context) {

    private val preferences: SharedPreferences by lazy { applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) }

    val identificationPreferences: IdentificationPreferences by lazy { IdentificationPreferences(preferences) }
    val nameOwnerPreferences: NameOwnerPreferences by lazy { NameOwnerPreferences(preferences) }

    companion object {
        private const val PREFERENCES_NAME = "com.mercadolibre.android.cardform.store"
    }
}
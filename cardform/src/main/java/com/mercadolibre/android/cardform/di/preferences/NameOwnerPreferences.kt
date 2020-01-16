package com.mercadolibre.android.cardform.di.preferences

import android.content.SharedPreferences

internal class NameOwnerPreferences(private val localPreferences: SharedPreferences) {

    fun saveNameOwner(nameOwner: String) {
        localPreferences.edit()?.apply {
            putString(NAME_OWNER, nameOwner)
            apply()
        }
    }

    fun getNameOwner() = localPreferences.getString(NAME_OWNER, "") ?: ""

    companion object {
        private const val NAME_OWNER = "name_owner"
    }
}
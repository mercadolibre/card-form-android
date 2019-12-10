package com.mercadolibre.android.cardform.di.preferences

import android.content.SharedPreferences

class IdentificationPreferences(private val localPreferences: SharedPreferences) {

    fun saveIdentificationId(id: String) {
        localPreferences.edit()?.apply {
            putString(IDENTIFICATION_ID, id)
            apply()
        }
    }

    fun saveIdentificationNumber(number: String) {
        localPreferences.edit()?.apply {
            putString(IDENTIFICATION_NUMBER, number)
            apply()
        }
    }

    fun getIdentificationId() = localPreferences.getString(IDENTIFICATION_ID, "") ?: ""

    fun getIdentificationNumber() = localPreferences.getString(IDENTIFICATION_NUMBER, "") ?: ""

    companion object {
        private const val IDENTIFICATION_ID = "id"
        private const val IDENTIFICATION_NUMBER = "number"
    }
}
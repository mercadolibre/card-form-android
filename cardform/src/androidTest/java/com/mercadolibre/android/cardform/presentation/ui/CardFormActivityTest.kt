package com.mercadolibre.android.cardform.presentation.ui

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import org.junit.Rule
import org.junit.Test

class CardFormActivityTest {

    private val cardForm by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-3671576383500204-072117-d275735575b2b95458be231afc00f14c-506902649",
            siteId = "MLA",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(ApplicationProvider.getApplicationContext<Application>(), CardFormActivity::class.java).putExtra(CARD_FORM_EXTRA, cardForm)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

    @Test
    fun test() {

    }

}

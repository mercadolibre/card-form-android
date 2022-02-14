package com.mercadolibre.android.cardform.presentation.ui.cardformactivity

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import org.junit.Rule

class CardFormActivityMLA: CardFormActivityTest() {

    private val cardFormMLA by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-3671576383500204-072117-d275735575b2b95458be231afc00f14c-506902649",
            siteId = "MLB",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(
            ApplicationProvider.getApplicationContext<Application>(),
            CardFormActivity::class.java
        ).putExtra(CARD_FORM_EXTRA, cardFormMLA)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)



}
package com.mercadolibre.android.cardform.presentation.ui.cardformactivity

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import org.junit.Rule

class CardFormActivityMLM: CardFormActivityTest() {

    private val cardFormMLA by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-5229115741788366-021722-2ae878852a13533ff1a23fb85b8f5135-1076275106",
            siteId = "MLM",
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
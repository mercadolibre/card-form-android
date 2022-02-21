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

    private val cardFormMLM by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-3964778276245137-021513-74efe11fd763bc23f136336969ef6d5b-1074687404",
            siteId = "MLM",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(
            ApplicationProvider.getApplicationContext<Application>(),
            CardFormActivity::class.java
        ).putExtra(CARD_FORM_EXTRA, cardFormMLM)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

}

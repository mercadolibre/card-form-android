package com.mercadolibre.android.cardform.presentation.ui.cardformactivity

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.ui.CardFormActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class CardFormActivityMLU: CardFormActivityTest() {

    private val cardFormMLU by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-1653641732285016-022113-7f3c59315dfd87f1118f0fbcd39540f3-1078001884",
            siteId = "MLU",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(
            ApplicationProvider.getApplicationContext<Application>(),
            CardFormActivity::class.java
        ).putExtra(CARD_FORM_EXTRA, cardFormMLU)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

    @Test
    fun when_insert_a_valid_ci_then_not_displayed_hint() {
        val ci = "95362156"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ci), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_otro_then_not_displayed_hint() {
        val otro = "16654654812-a3211k45"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(otro), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_ci_then_displayed_hint() {
        val ci = "0"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ci), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(invalidIDHint)))
    }

    @Test
    fun when_insert_a_invalid_otro_then_displayed_hint() {
        val otro = "--"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(otro), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(invalidIDHint)))
    }

}

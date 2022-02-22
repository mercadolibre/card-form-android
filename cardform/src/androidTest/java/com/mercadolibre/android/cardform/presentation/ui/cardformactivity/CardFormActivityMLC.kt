package com.mercadolibre.android.cardform.presentation.ui.cardformactivity

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
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
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test

class CardFormActivityMLC: CardFormActivityTest() {

    private val cardFormMLC by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-3964778276245137-021513-74efe11fd763bc23f136336969ef6d5b-1074687404",
            siteId = "MLC",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(
            ApplicationProvider.getApplicationContext<Application>(),
            CardFormActivity::class.java
        ).putExtra(CARD_FORM_EXTRA, cardFormMLC)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

    @Test
    fun when_insert_a_valid_rut_then_not_displayed_hint() {
        val rut = "132031967"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(0).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(rut), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_rut_then_displayed_hint() {
        val rut = "11111"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(0).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(rut), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

    @Test
    fun when_insert_a_valid_other_then_not_displayed_hint() {
        val other = "168755228"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(1).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(other), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_other_then_displayed_hint() {
        val other = "0101"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(1).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(other), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }
}
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

class CardFormActivityMPE: CardFormActivityTest() {

    private val cardFormMLC by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-4376526476119283-022217-b6fe04f6f0ae0b17068fbe9d0e24c264-1078773957",
            siteId = "MPE",
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
    fun when_insert_a_valid_dni_then_not_displayed_hint() {
        val dni = "13203196"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(0).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(dni), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_dni_then_displayed_hint() {
        val dni = "000000"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(0).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(dni), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

    @Test
    fun when_insert_a_valid_ce_then_not_displayed_hint() {
        val ce = "168755228"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(1).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ce), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_ce_then_displayed_hint() {
        val ce = "000000"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(1).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ce), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_ruc_then_not_displayed_hint() {
        val ruc = "16875522822"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(2).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ruc), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_ruc_then_displayed_hint() {
        val ruc = "000000"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(2).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ruc), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_other_then_not_displayed_hint() {
        val other = "168755228"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(3).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(other), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_other_then_displayed_hint() {
        val other = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        Espresso.onData(Matchers.anything()).atPosition(3).perform(click())
        onView(AllOf.allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(other), closeSoftKeyboard())
        onView(AllOf.allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }
}


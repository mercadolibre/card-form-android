package com.mercadolibre.android.cardform.presentation.ui.cardformactivity

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
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
import org.junit.Rule
import org.junit.Test

class CardFormActivityMCO: CardFormActivityTest() {

    private val cardFormMCO by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-3964778276245137-021513-74efe11fd763bc23f136336969ef6d5b-1074687404",
            siteId = "MCO",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(
            ApplicationProvider.getApplicationContext<Application>(),
            CardFormActivity::class.java
        ).putExtra(CARD_FORM_EXTRA, cardFormMCO)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

    @Test
    fun when_insert_a_valid_CC_then_not_displayed_hint() {
        val cc = "48512369855954561651"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(cc), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_CE_then_not_displayed_hint() {
        val ce = "12345698787878787879"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(1).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(ce), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_NIT_then_not_displayed_hint() {
        val NIT = "65465165054654654879"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(2).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(NIT), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_otro_then_not_displayed_hint() {
        val otro = "12312341246523465465"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(3).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(otro), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_CC_then_displayed_hint() {
        val CC = "0"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(CC), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(invalidIDHint)))
    }

    @Test
    fun when_insert_a_invalid_CE_then_displayed_hint() {
        val CE = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(1).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(CE), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(invalidIDHint)))
    }

    @Test
    fun when_insert_a_invalid_NIT_then_displayed_hint() {
        val NIT = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(2).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(NIT), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(invalidIDHint)))
    }

    @Test
    fun when_insert_a_invalid_otro_then_displayed_hint() {
        val otro = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(3).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(otro), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(invalidIDHint)))
    }
}
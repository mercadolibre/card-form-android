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
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class CardFormActivityMLA: CardFormActivityTest() {

    private val cardFormMLA by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "APP_USR-3671576383500204-072117-d275735575b2b95458be231afc00f14c-506902649",
            siteId = "MLA",
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

    @Test
    fun when_insert_a_valid_DNI_then_not_displayed_hint() {
        val dni = "85098456"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(dni), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_cedula_then_not_displayed_hint() {
        val cedula = "456289654"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(1).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(cedula), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_lc_then_not_displayed_hint() {
        val lc = "1654879"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(2).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(lc), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_le_then_not_displayed_hint() {
        val le = "1654879"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(3).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(le), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_otro_then_not_displayed_hint() {
        val otro = "45156847841235489987"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(4).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(otro), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_DNI_then_displayed_hint() {
        val dni = "0"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(dni), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

    @Test
    fun when_insert_a_invalid_lc_then_displayed_hint() {
        val lc = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(2).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(lc), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

    @Test
    fun when_insert_a_invalid_le_then_displayed_hint() {
        val le = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(3).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(le), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

    @Test
    fun when_insert_a_invalid_otro_then_displayed_hint() {
        val otro = "0"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(Matchers.anything()).atPosition(4).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(otro), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

}


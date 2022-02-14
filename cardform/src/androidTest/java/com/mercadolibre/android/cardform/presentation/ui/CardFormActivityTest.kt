package com.mercadolibre.android.cardform.presentation.ui

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.ui.base.UIBaseTest
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class CardFormActivityTest : UIBaseTest() {

    private val cardForm by lazy {
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
        ).putExtra(CARD_FORM_EXTRA, cardForm)
    }

    private val cardNumberValid = "5067268650517446"
    private val cardNameValid = "JOSE SILVA"

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

    @Test
    fun when_insert_a_valid_card_number_then_not_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.numberCardEditText))))
            .check(matches(withText("")))
    }

    @Test
    fun when_insert_a_valid_card_name_then_not_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.nameCardEditText))))
            .check(matches(withText("As it shows on the card")))
    }

    @Test
    fun when_insert_card_number_then_complete_the_card_drawer() {
        val expectedCard = "5067  2686  5051  7446"
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        onView(withId(com.meli.android.carddrawer.R.id.cho_card_number)).check(matches(withText(expectedCard)))
    }

    @Test
    fun when_insert_card_name_then_complete_the_card_drawer() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.nameCardEditText))))
            .check(matches(withText("As it shows on the card")))
    }

    @Test
    fun when_insert_card_number_invalid_then_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText("5067 2686 5051 0000"), closeSoftKeyboard())
        await()
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.numberCardEditText))))
            .check(matches(withText(R.string.cf_card_number_info_hint)))
    }

    @Test
    fun when_insert_card_name_invalid_then_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText("''''"), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.nameCardEditText))))
            .check(matches(withText("Complete using only letters")))
    }

}

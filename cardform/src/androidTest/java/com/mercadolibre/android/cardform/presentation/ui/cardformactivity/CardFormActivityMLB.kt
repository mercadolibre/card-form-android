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
import org.hamcrest.Matchers.anything
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class CardFormActivityMLB: CardFormActivityTest() {

    private val cardFormMLB by lazy {
        CardForm.Builder.withAccessToken(
            accessToken = "TEST-918970358866126-022319-bb39176fa6c5a05476da0269a806886d-1079451593",
            siteId = "MLB",
            flowId = "test_flow"
        ).build()
    }

    private val intent by lazy {
        Intent(
            ApplicationProvider.getApplicationContext<Application>(),
            CardFormActivity::class.java
        ).putExtra(CARD_FORM_EXTRA, cardFormMLB)
    }

    @get:Rule
    internal var activityScenarioRule = ActivityScenarioRule<CardFormActivity>(intent)

    @Test
    fun when_insert_a_valid_cpf_then_not_displayed_hint() {
        val cpf = "09164640086"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(cpf), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_valid_cnpj_then_not_displayed_hint() {
        val cnpj = "35167486000131"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(cnpj), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText("")))
        onView(withId(R.id.next)).perform(click())
    }

    @Test
    fun when_insert_a_invalid_cpf_then_displayed_hint() {
        val cpf = "00000000000"
        initializeScreenToDocumentInsertion()
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(cpf), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

    @Test
    fun when_insert_a_invalid_cnpj_then_displayed_hint() {
        val cnpj = "00000000000000"
        initializeScreenToDocumentInsertion()
        onView(withId(R.id.identificationTypes)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.identificationEditText))))
            .perform(typeText(cnpj), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.identificationEditText))))
            .check(matches(withText(INVALID_ID_HINT)))
    }

}

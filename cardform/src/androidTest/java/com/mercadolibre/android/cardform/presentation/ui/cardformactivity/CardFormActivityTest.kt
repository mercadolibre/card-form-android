package com.mercadolibre.android.cardform.presentation.ui.cardformactivity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.ui.base.UIBaseTest
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Test

open class CardFormActivityTest : UIBaseTest() {

    protected val cardNumberValid = "5067268650517446"
    protected val cardNameValid = "JOSE SILVA"
    protected val cardExpirationValid = "1029"
    protected val cardCVV = "123"

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
    fun when_insert_a_valid_expiration_then_not_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.expirationEditText))))
            .perform(typeText(cardExpirationValid), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.expirationEditText))))
            .check(matches(withText("MM/YY")))
    }

    @Test
    fun when_insert_cvv_then_not_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.expirationEditText))))
            .perform(typeText(cardExpirationValid), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.cvvCodeEditText))))
            .perform(typeText(cardCVV), closeSoftKeyboard())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.cvvCodeEditText))))
            .check(matches(withText("CVV")))
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
        onView(withId(com.meli.android.carddrawer.R.id.cho_card_name)).check(matches(withText(cardNameValid)))
    }

    @Test
    fun when_insert_card_expiration_then_complete_the_card_drawer() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.expirationEditText))))
            .perform(typeText(cardExpirationValid), closeSoftKeyboard())
        onView(withId(com.meli.android.carddrawer.R.id.cho_card_date)).check(matches(withText("10/29")))
    }

    @Test
    fun when_insert_card_cvv_then_complete_the_card_drawer() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.expirationEditText))))
            .perform(typeText(cardExpirationValid), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.cvvCodeEditText))))
            .perform(typeText(cardCVV), closeSoftKeyboard())
        onView(withId(com.meli.android.carddrawer.R.id.cho_card_code_front)).check(matches(withText(cardCVV)))
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

    @Test
    fun when_insert_card_expiration_invalid_then_displayed_hint() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.nameCardEditText))))
            .perform(typeText(cardNameValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.expirationEditText))))
            .perform(typeText("1019"), closeSoftKeyboard())
        onView(withId(R.id.next)).perform(click())
        onView(allOf(withId(R.id.infoInput), isDescendantOfA(withId(R.id.expirationEditText))))
            .check(matches(withText("Invalid date")))
    }

    @Test
    fun when_entering_card_number_then_card_number_should_be_disabled() {
        onView(withId(R.id.back)).check(matches(not(ViewMatchers.isEnabled())))
    }

    @Test
    fun when_entering_card_name_and_press_back_then_should_come_back() {
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .perform(typeText(cardNumberValid), closeSoftKeyboard())
        await()
        onView(withId(R.id.next)).perform(click())
        onView(withId(R.id.back)).perform(click())
        onView(allOf(withId(R.id.input), isDescendantOfA(withId(R.id.numberCardEditText))))
            .check(matches(isDisplayed()))
    }

}

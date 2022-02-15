package com.mercadolibre.android.cardform.presentation.ui.base

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.mercadolibre.android.cardform.presentation.ui.util.ElapsedTimeIdlingResource
import org.junit.After

open class UIBaseTest {

    private val idlingResource: IdlingResource by lazy {
        ElapsedTimeIdlingResource()
    }

    protected fun await() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun unload() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

}

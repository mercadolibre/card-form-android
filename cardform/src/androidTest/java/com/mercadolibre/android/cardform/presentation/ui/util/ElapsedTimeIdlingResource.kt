package com.mercadolibre.android.cardform.presentation.ui.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback

class ElapsedTimeIdlingResource(private val waitingTime: Long = 5) : IdlingResource {

    private val startTime: Long = System.currentTimeMillis()
    private var resourceCallback: ResourceCallback? = null

    override fun getName(): String {
        return ElapsedTimeIdlingResource::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

}

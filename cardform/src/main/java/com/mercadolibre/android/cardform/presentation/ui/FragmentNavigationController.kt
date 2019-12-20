package com.mercadolibre.android.cardform.presentation.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.extensions.addKeyBoardListener
import com.mercadolibre.android.cardform.presentation.extensions.hideKeyboard
import com.mercadolibre.android.cardform.presentation.extensions.showKeyboard
import com.mercadolibre.android.cardform.presentation.ui.formentry.*

object FragmentNavigationController {
    private lateinit var formViewPager: InputFormViewPager
    private var currentFragment: InputFragment? = null
    private var showIssuers = false
    private var keyboardShowing = false
    private var localCurrentItem = 0
    private var progressByStep = 0
    private const val PROGRESS_DEFAULT = 80

    fun init(manager: FragmentManager?, viewPager: InputFormViewPager) {
        formViewPager = viewPager

        with(formViewPager) {
            setScrollEnable(false)
            adapter = FormInputViewPagerAdapter(manager)

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(position: Int) = Unit

                override fun onPageScrolled(position: Int, p1: Float, p2: Int) = Unit

                override fun onPageSelected(position: Int) {
                    getCurrentInput()
                        ?.apply {
                            if (localCurrentItem < position)
                                fromLeft()
                            else if (localCurrentItem > position)
                                fromRight()
                        }
                    localCurrentItem = position
                }
            })

            post {
                if (currentFragment !is IssuersFragment) {
                    currentFragment = getCurrentInput()
                }
                calculateProgress()
            }
        }
    }

    fun toBack(): Boolean {
        val previousPosition = formViewPager.currentItem - 1

        if (previousPosition < 0) return false

        currentFragment?.toBack(previousPosition) {

            currentFragment = if (currentFragment is IssuersFragment) {
                getCurrentInput()?.run {
                    focusableInTouchMode(false)
                    showKeyboard(currentFragment)
                    this
                }
            } else {
                formViewPager.currentItem = it
                getCurrentInput()
            }
        }

        return true
    }

    fun toNext(): Boolean {
        val nextPosition = formViewPager.currentItem + 1
        var notFinished = true

        currentFragment?.toNext(nextPosition, move = {
            if (it >= FormType.getValues().size) {
                if (!showIssuers) {
                    notFinished = false
                } else {
                    currentFragment?.apply {
                        focusableInTouchMode(true)
                        showIssuerFragment(fragmentManager)
                    }
                }
            } else {
                formViewPager.currentItem = it
                currentFragment = getCurrentInput()
            }
        })

        return notFinished
    }

    fun setAdditionalSteps(steps: List<String>) {
        var newSteps = steps
        if (steps.contains("issuers")) {
            showIssuers = true
            newSteps = steps.filter { it != "issuers" }
        } else {
            showIssuers = false
        }
        FormType.setAdditionalSteps(newSteps)
        calculateProgress()
        formViewPager.adapter?.notifyDataSetChanged()
    }

    private fun calculateProgress() {
        progressByStep = PROGRESS_DEFAULT / FormType.getValues().size
    }

    private fun showIssuerFragment(manager: FragmentManager?) {
        val issuerFragment = IssuersFragment()
        manager?.beginTransaction()?.apply {
            setCustomAnimations(R.anim.cf_push_up_in, 0, 0, R.anim.cf_push_down_out)
            replace(
                R.id.rootCardForm, issuerFragment,
                IssuersFragment.TAG
            )
            addToBackStack(IssuersFragment.TAG)
            commitAllowingStateLoss()
            manager.executePendingTransactions()
        }
        currentFragment = issuerFragment
        hideKeyboard(currentFragment)
    }

    private fun getCurrentInput() = (formViewPager.adapter as FormInputViewPagerAdapter)
        .currentFragment as InputFragment?

    fun addKeyBoardListener(rootFragment: Fragment) {
        rootFragment.addKeyBoardListener(
            onKeyBoardOpen = { keyboardShowing = true },
            onKeyBoardClose = { keyboardShowing = false }
        )
    }

    fun hideKeyboard(fragment: Fragment?) {
        if (keyboardShowing) {
            fragment?.hideKeyboard()
        }
    }

    fun showKeyboard(fragment: Fragment?) {
        if (!keyboardShowing) {
            fragment?.showKeyboard()
        }
    }

    fun getProgress() =
        progressByStep

    fun reset() {
        currentFragment = null
        localCurrentItem = 0
        FormType.reset()
    }
}
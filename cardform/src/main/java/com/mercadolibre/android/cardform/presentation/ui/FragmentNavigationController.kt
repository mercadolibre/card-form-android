package com.mercadolibre.android.cardform.presentation.ui

import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.presentation.ui.formentry.*

object FragmentNavigationController {
    private lateinit var formViewPager: InputFormViewPager
    private var currentFragment: InputFragment? = null
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
                    getCurrentInput()?.apply {
                        if (localCurrentItem < position) {
                            fromLeft()
                        } else if (localCurrentItem > position) {
                            fromRight()
                        }
                    }
                }
            })

            post {
                if (FormType.getValue(localCurrentItem).fromPager) {
                    currentFragment = getCurrentInput()
                }
                calculateProgress()
            }
        }
    }

    fun toBack(): Boolean {
        val previousPosition = localCurrentItem - 1

        if (previousPosition < 0) return false

        currentFragment?.toBack(previousPosition) {

            currentFragment = if (FormType.getValue(localCurrentItem).fromPager) {
                formViewPager.currentItem = it
                getCurrentInput()
            } else {
                getCurrentInput()?.apply { focusableInTouchMode(false) }
            }
            localCurrentItem = it
        }

        return true
    }

    fun toNext(): Boolean {
        val nextPosition = localCurrentItem + 1
        var notFinished = true

        currentFragment?.toNext(nextPosition, move = {
            if (it < FormType.getValues().size) {
                currentFragment = if (FormType.getValue(it).fromPager) {
                    formViewPager.currentItem = it
                    getCurrentInput()
                } else {
                    currentFragment?.run {
                        focusableInTouchMode(true)
                        addNextFragment(fragmentManager, FormType.getValue(it).getFragment())
                    }
                }
                localCurrentItem = it
            } else {
                notFinished = false
            }
        })

        return notFinished
    }

    fun setAdditionalSteps(steps: List<String>) {
        FormType.setAdditionalSteps(steps)
        calculateProgress()
        formViewPager.adapter?.notifyDataSetChanged()
    }

    private fun calculateProgress() {
        progressByStep = PROGRESS_DEFAULT / FormType.getFromPager().size
    }

    private fun addNextFragment(
        manager: FragmentManager?,
        fragment: InputFragment
    ): InputFragment? {
        return manager?.beginTransaction()?.run {
            setCustomAnimations(R.anim.cf_push_up_in, 0, 0, R.anim.cf_push_down_out)
            replace(
                R.id.rootCardForm, fragment,
                fragment.getInputTag()
            )
            addToBackStack(fragment.getInputTag())
            commitAllowingStateLoss()
            manager.executePendingTransactions()
            fragment
        }
    }

    private fun getCurrentInput() = (formViewPager.adapter as FormInputViewPagerAdapter)
        .getCurrentStep() as InputFragment?

    fun getProgress() = progressByStep

    fun reset() {
        currentFragment = null
        localCurrentItem = 0
        FormType.reset()
    }
}
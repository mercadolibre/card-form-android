package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

class FormInputViewPagerAdapter(
    manager: FragmentManager?
) : FragmentStatePagerAdapter(manager) {

    private var currentFragment: Fragment? = null

    override fun getItem(position: Int) = FormType.getFromPager()[position].getFragment()

    override fun getItemPosition(fragment: Any): Int {
        return if (fragment is CardNumberFragment) {
            PagerAdapter.POSITION_UNCHANGED
        } else {
            (fragment as InputFragment).refreshData()
            PagerAdapter.POSITION_NONE
        }
    }

    override fun getCount() = FormType.getFromPager().size
    fun getCurrentStep() = currentFragment

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (currentFragment != `object`) {
            currentFragment = `object` as Fragment
        }
        super.setPrimaryItem(container, position, `object`)
    }
}
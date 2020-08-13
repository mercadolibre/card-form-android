package com.mercadolibre.android.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.cardform.internal.CardFormWithFragment

class OneTapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_one_tap)
        if (supportFragmentManager.findFragmentByTag(OneTapFragment.TAG) == null) {
            with(supportFragmentManager.beginTransaction()) {
                replace(R.id.container, OneTapFragment.newInstance(), OneTapFragment.TAG)
                commitAllowingStateLoss()
            }
        }
    }

    override fun onBackPressed() {
        val cardFormFragment = supportFragmentManager.findFragmentByTag(CardFormWithFragment.TAG)
        cardFormFragment?.childFragmentManager?.apply {
            if (backStackEntryCount > 0) {
                popBackStack()
                return
            }
        }
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, OneTapActivity::class.java))
        }
    }
}
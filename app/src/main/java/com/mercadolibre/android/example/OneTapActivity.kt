package com.mercadolibre.android.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class OneTapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_one_tap)
        if (supportFragmentManager.findFragmentByTag(OneTapFragment.TAG) == null) {
            with(supportFragmentManager.beginTransaction()) {
                replace(R.id.container, OneTapFragment.newInstance(), OneTapFragment.TAG)
                addToBackStack(OneTapFragment.TAG)
                commitAllowingStateLoss()
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, OneTapActivity::class.java))
        }
    }
}
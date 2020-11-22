package com.mercadolibre.android.cardform.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadolibre.android.cardform.R

internal class CardFormWebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_form_web)

        intent.extras?.let { extras ->
            if (supportFragmentManager.findFragmentByTag(CardFormWebViewFragment.TAG) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.web_view_fragment_container,
                        CardFormWebViewFragment.newInstance(extras),
                        CardFormWebViewFragment.TAG
                    ).commitAllowingStateLoss()
            }
        }
    }


    companion object {
        fun start(activity: AppCompatActivity, requestCode: Int, bundle: Bundle) {
            val intent = Intent(activity, CardFormWebActivity::class.java)
            intent.putExtras(bundle)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
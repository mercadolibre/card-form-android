package com.mercadolibre.android.cardform.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.internal.CardFormWithFragment

internal class CardFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_form)
        if (supportFragmentManager.findFragmentByTag(CardFormWithFragment.TAG) == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.container,
                    CardFormFragment.newInstance(false, intent.getParcelableExtra(EXTRA_CARD_FORM)),
                    CardFormWithFragment.TAG)
                commitAllowingStateLoss()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.slide_left_to_right_in,
            R.anim.slide_left_to_right_out
        )
    }

    companion object {
        private const val EXTRA_CARD_FORM = "card_form"

        fun start(activity: AppCompatActivity, requestCode: Int, cardForm: CardForm) {
            val intent = Intent(activity, CardFormActivity::class.java)
            intent.putExtra(EXTRA_CARD_FORM, cardForm)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
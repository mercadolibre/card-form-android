package com.mercadolibre.android.cardform.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.EXIT_ANIM_EXTRA
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.internal.CardFormWithFragment

internal class CardFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_form)
        if (supportFragmentManager.findFragmentByTag(CardFormWithFragment.TAG) == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.container,
                    CardFormFragment.newInstance(false, intent.getParcelableExtra(CARD_FORM_EXTRA),
                        intent.getIntExtra(EXIT_ANIM_EXTRA, R.anim.slide_right_to_left_out)),
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

        private fun getIntent(context: Context, cardForm: CardForm, exitAnim: Int?) =
            Intent(context, CardFormActivity::class.java).also {
                it.putExtra(CARD_FORM_EXTRA, cardForm)
                it.putExtra(EXIT_ANIM_EXTRA, exitAnim)
            }

        fun start(fragment: Fragment, requestCode: Int, cardForm: CardForm, exitAnim: Int?) {
            fragment.context?.let {
                fragment.startActivityForResult(getIntent(it, cardForm, exitAnim), requestCode)
            }
        }

        fun start(activity: AppCompatActivity, requestCode: Int, cardForm: CardForm, exitAnim: Int?) {
            activity.startActivityForResult(getIntent(activity, cardForm, exitAnim), requestCode)
        }
    }
}
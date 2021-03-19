package com.mercadolibre.android.cardform.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.CARD_FORM_EXTRA
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.EXIT_ANIM_EXTRA
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.internal.CardFormWithFragment
import kotlinx.android.synthetic.main.activity_card_form.view.*

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
        setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.apply {
                window.decorView.status_bar_place_holder.apply {
                    layoutParams.height = insets.stableInsetTop
                    visibility = View.VISIBLE
                    setBackgroundColor(
                        resources.getColor(R.color.ui_meli_red))
                }
            }
            insets
        }
        setUpWindow()
    }

    @SuppressLint("InlinedApi")
    private fun setUpWindow() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
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
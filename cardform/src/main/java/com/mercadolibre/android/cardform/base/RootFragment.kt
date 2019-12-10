package com.mercadolibre.android.cardform.base

import android.os.Bundle
import android.view.View
import com.mercadolibre.android.cardform.di.Dependencies

abstract class RootFragment<T : BaseViewModel> : BaseFragment<T>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            Dependencies.instance.initialize(this, it.getParcelable(ARG_CARD_FORM)!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let { viewModel.recoverFromBundle(savedInstanceState) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.storeInBundle(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        Dependencies.instance.clean()
    }

    companion object {
        const val ARG_CARD_FORM = "card_form"
    }
}
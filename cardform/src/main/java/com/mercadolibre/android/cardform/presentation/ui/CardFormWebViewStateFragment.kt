package com.mercadolibre.android.cardform.presentation.ui

import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.BaseFragment
import com.mercadolibre.android.cardform.di.sharedViewModel
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.viewmodel.CardFormWebViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CardFormWebViewStateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
internal class CardFormWebViewStateFragment : BaseFragment<CardFormWebViewModel>() {

    override val viewModel: CardFormWebViewModel by sharedViewModel { activity!! }
    override val rootLayout = R.layout.fragment_card_form_web_view_state


    override fun bindViewModel() {
        viewModel.webUiStateLiveData.nonNullObserve(viewLifecycleOwner) {

        }
    }


    companion object {
        const val TAG = "state_web_view"

        @JvmStatic
        fun newInstance() = CardFormWebViewStateFragment()
    }
}
package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.mercadolibre.android.cardform.base.BaseFragment
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController
import com.mercadolibre.android.cardform.presentation.viewmodel.InputFormViewModel

typealias MoveTo = ((position: Int) -> Unit)

/**
 * A simple [Fragment] subclass.
 */

abstract class InputFragment : BaseFragment<InputFormViewModel>() {
    override val viewModelClass = InputFormViewModel::class.java
    protected var isInputValid = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isInputValid = savedInstanceState?.getBoolean(INPUT_VALID, false) ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(INPUT_VALID, isInputValid)
    }

    open fun focusableInTouchMode(focusable: Boolean) = Unit

    protected open fun showError() = Unit

    open fun fromLeft() {
        viewModel.updateProgressData(FragmentNavigationController.getProgress())
    }

    open fun fromRight() {
        viewModel.updateProgressData(-FragmentNavigationController.getProgress())
    }

    open fun toBack(position: Int, move: MoveTo) = move.invoke(position)

    open fun toNext(position: Int, move: MoveTo) = if (isInputValid) {
        move.invoke(position)
    } else {
        showError()
    }

    companion object {
        private const val INPUT_VALID = "input_valid"
    }
}
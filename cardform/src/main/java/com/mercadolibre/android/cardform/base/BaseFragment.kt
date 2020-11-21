package com.mercadolibre.android.cardform.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mercadolibre.android.cardform.di.Dependencies

internal abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: T
    protected abstract val viewModelClass: Class<T>
    protected abstract val rootLayout: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(rootLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = Dependencies.instance.viewModelModule!!.get(this, viewModelClass)
        bindViewModel()
    }

    protected open fun bindViewModel() = Unit
}
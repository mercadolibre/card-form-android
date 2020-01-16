package com.mercadolibre.android.cardform.presentation.ui.custom

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mercadolibre.android.cardform.R

internal class ProgressFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view = inflater.inflate(R.layout.cf_progress_fragment, container, false)
        dialog?.apply {
            retainInstance = false
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
        return view
    }

    companion object {
        const val TAG = "progress_fragment"
        fun newInstance() =
            ProgressFragment()
    }
}
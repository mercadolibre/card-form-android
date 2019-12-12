package com.mercadolibre.android.example

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.presentation.extensions.pushDownOut
import com.mercadolibre.android.cardform.presentation.extensions.pushUpIn
import kotlinx.android.synthetic.main.fragment_one_tap.*

/**
 * A simple [Fragment] subclass.
 * Use the [OneTapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneTapFragment : Fragment() {

    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFirstTime = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one_tap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirstTime) startAnim.pushUpIn()

        startAnim.setOnClickListener {
            activity?.apply {
                isFirstTime = true
                startAnim.pushDownOut()
                CardForm.Builder.withAccessToken("APP_USR-6519316523937252-070516-964fafa7e2c91a2c740155fcb5474280__LA_LD__-261748045", "MLA").build()
                    .start(supportFragmentManager, REQUEST_CODE, R.id.container)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {result ->
            context?.let {
                Toast.makeText(it, result.dataString, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val TAG = "ONE_TAP"
        private const val REQUEST_CODE = 213

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment OneTapFragment.
         */
        fun newInstance() = OneTapFragment()
    }
}
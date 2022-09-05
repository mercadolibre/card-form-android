package com.mercadolibre.android.example

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mercadolibre.android.cardform.internal.CardFormWithFragment
import com.mercadolibre.android.cardform.internal.LifecycleListener
import com.mercadolibre.android.example.databinding.FragmentOneTapBinding

/**
 * A simple [Fragment] subclass.
 * Use the [OneTapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneTapFragment : Fragment(), LifecycleListener {

    private var _binding: FragmentOneTapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneTapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startAnim.setOnClickListener {
            activity?.apply {
                CardFormWithFragment.Builder.withAccessToken(
                    "TEST-814372640131976-032220-46f58f2b280832e476f441167b605310-1094281250",
                    "MLA", "test_flow")
                    .setThirdPartyCard(true, true)
                    .build()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
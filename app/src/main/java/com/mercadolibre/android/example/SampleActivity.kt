package com.mercadolibre.android.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.CardForm.Companion.RESULT_CARD_ID_KEY
import com.mercadolibre.android.cardform.internal.CardFormWeb
import com.mercadolibre.android.cardform.presentation.delegate.viewBinding
import com.mercadolibre.android.cardform.service.CardFormIntent
import com.mercadolibre.android.example.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {

    private val binding: ActivitySampleBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.fromCardAssociation.setOnClickListener {
            CardForm.Builder.withAccessToken(
                "APP_USR-3671576383500204-072117-d275735575b2b95458be231afc00f14c-506902649",
                "MLA", "test_flow"
            )
                .build()
                .start(this, REQUEST_CODE)
        }

        binding.fromOneTap.setOnClickListener {
            OneTapActivity.start(this)
        }

        binding.fromWebView.setOnClickListener {
            CardFormWeb
                .Builder
                .withAccessToken(
                    "TEST-5476935244572826-112116-4dfe0023f3a444c1e42013b05336f027-675049545",
                    "MLC", "test_flow"
                )
                .setCardFormHandler(CardFormIntent(this, SampleService::class.java))
                .build()
                .start(this, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            data?.let { result ->
                val cardId = result.getStringExtra(RESULT_CARD_ID_KEY)
                Toast.makeText(this, "Card Id: $cardId", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 213
    }
}
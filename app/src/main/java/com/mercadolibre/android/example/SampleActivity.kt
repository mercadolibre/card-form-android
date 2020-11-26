package com.mercadolibre.android.example

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.internal.CardFormWeb
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        fromCardAssociation.setOnClickListener {
            CardForm.Builder.withAccessToken(
                "APP_USR-3671576383500204-072117-d275735575b2b95458be231afc00f14c-506902649",
                "MLA", "test_flow").build()
                .start(this, REQUEST_CODE)
        }

        fromOneTap.setOnClickListener {
            OneTapActivity.start(this)
        }

        fromWebView.setOnClickListener {
            CardFormWeb
                .Builder
                .buildWithAccessToken(
                "TEST-5476935244572826-112116-4dfe0023f3a444c1e42013b05336f027-675049545",
                "MLC", "test_flow")
                .build()
                .start(this, REQUEST_CODE,
                    "chaca",
                    "chaca@gmail.com",
                    "https://www.comercio.cl/return_inscription")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            data?.let { result ->
                Toast.makeText(this, result.dataString, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 213
    }
}
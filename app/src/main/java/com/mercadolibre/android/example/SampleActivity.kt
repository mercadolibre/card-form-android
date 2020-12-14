package com.mercadolibre.android.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.cardform.CardForm
import com.mercadolibre.android.cardform.CardForm.Companion.RESULT_CARD_ID_KEY
import com.mercadolibre.android.cardform.internal.CardFormWeb
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        Fresco.initialize(this)

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
                "TEST-1805086732367934-111018-06d46abf732b1d4c08198eacb9eb039a-667901864",
                "MLC", "test_flow")
                .setCardFormHandler(CardFormHandlerImpl())
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
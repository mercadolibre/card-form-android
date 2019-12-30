package com.mercadolibre.android.example

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mercadolibre.android.cardform.CardForm
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        fromCardAssociation.setOnClickListener {
            CardForm.Builder.withAccessToken(
                "APP_USR-7092-122619-fc2376471063df48cf0c9fcd26e00729-506902649",
                "MLA").build()
                .start(this, REQUEST_CODE)
        }

        fromOneTap.setOnClickListener {
            OneTapActivity.start(this)
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
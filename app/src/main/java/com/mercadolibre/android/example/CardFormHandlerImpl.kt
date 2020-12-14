package com.mercadolibre.android.example

import android.os.Bundle
import android.util.Log
import com.mercadolibre.android.cardform.CardForm.Companion.RESULT_CARD_ID_KEY
import com.mercadolibre.android.cardform.service.CardFormHandler
import kotlinx.android.parcel.Parcelize

@Parcelize
class CardFormHandlerImpl: CardFormHandler() {

    override fun onProcessResult(resultData: Bundle) {
        resultData.getString(RESULT_CARD_ID_KEY)?.also {
            Log.i("CARD_FORM", "Process card with id: $it...")
            postDelayed({
                Log.i("CARD_FORM", "Process finished")
                onProcessComplete()
            }, 5000)
        } ?: error("card id data should be not null")
    }
}
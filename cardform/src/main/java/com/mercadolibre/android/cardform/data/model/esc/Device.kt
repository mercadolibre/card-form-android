package com.mercadolibre.android.cardform.data.model.esc

import android.content.Context
import com.mercadopago.android.px.addons.ESCManagerBehaviour

internal class Device(context: Context, escManager: ESCManagerBehaviour) {
    val fingerprint = Fingerprint(context, escManager)
}

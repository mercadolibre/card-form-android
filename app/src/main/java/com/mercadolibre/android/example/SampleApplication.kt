package com.mercadolibre.android.example

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}
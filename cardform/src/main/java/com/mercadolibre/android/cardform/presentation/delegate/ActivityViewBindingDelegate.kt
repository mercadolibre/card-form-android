package com.mercadolibre.android.cardform.presentation.delegate

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>
) : ReadOnlyProperty<Activity, T> {

    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        binding?.let { return it }

        val inflatedMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)
        val invokeLayout = inflatedMethod.invoke(null, thisRef.layoutInflater) as T

        thisRef.setContentView(invokeLayout.root)

        return invokeLayout.also { this.binding = it }
    }
}

inline fun <reified T : ViewBinding> Activity.viewBinding() = ActivityViewBindingDelegate(T::class.java)
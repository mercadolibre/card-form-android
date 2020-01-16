package com.mercadolibre.android.cardform.base

internal interface Mapper<T, V> {
    fun map(model: V): T
}
package com.mercadolibre.android.cardform.base

interface Mapper<T, V> {
    fun map(model: V): T
}
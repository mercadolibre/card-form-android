package com.mercadolibre.android.cardform.presentation.ui.custom

import android.text.Editable
import android.text.TextWatcher

internal open class MaskWatcher(private val mask: String) : TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(
        charSequence: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) =
        Unit

    override fun afterTextChanged(editable: Editable) {

        if (isRunning) {
            return
        }

        val editableLength = editable.length

        if (isDeleting) {
            if (editableLength > 0 && mask.isNotEmpty() && mask[(editableLength - 1)] != '$') {
                editable.delete(editableLength - 1, editableLength)
            }
            return
        }

        isRunning = true

        if (editableLength < mask.length && editableLength > 0) {
            if (mask[editableLength] != '$') {
                setCharMask(editable, mask[editableLength]) {
                    editable.append(it)
                }
            } else if (mask[editableLength - 1] != '$' && mask[editableLength - 1] != editable[editableLength - 1]) {
                setCharMask(editable, mask[editableLength - 1]) {
                    editable.insert(editableLength - 1, it.toString())
                }
            }
        }

        isRunning = false
    }

    fun update(editable: Editable, text: String, editableAction: (text: String) -> Unit) {
        with(editable) {
            val oldFilters = filters
            filters = emptyArray()
            editableAction(text)
            filters = oldFilters
        }
    }

    private fun setCharMask(editable: Editable, char: Char, editableAction: (char: Char) -> Unit) {
        with(editable) {
            val oldFilters = filters
            filters = emptyArray()
            editableAction(char)
            filters = oldFilters
        }
    }
}
package com.mercadolibre.android.cardform

import com.mercadolibre.android.cardform.data.model.response.FieldsSetting

internal object FieldsSettingProvider {

    fun makeFieldSetting(autocomplete: Boolean?) =
        FieldsSetting(
            "name",
            40,
            "text",
            "Card holder's name, hintMessage=As it shows on the card",
            "",
            "^[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]+(([',. -][a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ ])?[a-zA-ZñÑáàâãéèêẽíìîóòôõúùûÁÀÂÃÉÈÊẼÍÌÎÓÒÔÕÚÙÛ]*)*$",
            "Complete using only letters",
            "",
            autocomplete
        )
}

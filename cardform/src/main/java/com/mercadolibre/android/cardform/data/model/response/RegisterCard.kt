package com.mercadolibre.android.cardform.data.model.response

data class RegisterCard (
    val enabled : Boolean,
    val paymentMethod : PaymentMethod,
    val cardUi : CardUi,
    val additionalSteps : List<String>,
    val issuers : List<Issuer>,
    val fieldsSetting : List<FieldsSetting>,
    val identificationTypes : List<IdentificationTypes>
)
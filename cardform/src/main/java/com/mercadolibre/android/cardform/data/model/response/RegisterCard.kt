package com.mercadolibre.android.cardform.data.model.response

internal data class RegisterCard(
    val formTitle: String,
    val escEnabled: Boolean,
    val paymentMethod: PaymentMethod,
    val cardUi: CardUi,
    val additionalSteps: List<String>,
    val issuers: List<Issuer>,
    val fieldsSetting: List<FieldsSetting>,
    val identificationTypes: List<IdentificationTypes>
)
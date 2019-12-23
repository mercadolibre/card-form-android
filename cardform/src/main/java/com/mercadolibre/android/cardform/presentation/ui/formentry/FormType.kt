package com.mercadolibre.android.cardform.presentation.ui.formentry

enum class FormType(private val type: String) {

    CARD_NUMBER("card_number") {
        override var optional = false
        override fun getFragment() = CardNumberFragment()
    },
    CARD_NAME("name") {
        override fun getFragment() = CardNameFragment()
    },
    CARD_SECURITY("card_security") {
        override var optional = false
        override fun getFragment() = SecurityFragment()
    },
    CARD_IDENTIFICATION("identification_types") {
        override fun getFragment() = IdentificationFragment()
    },
    ISSUERS("issuers") {
        override var fromPager = false
        override fun getFragment() = IssuersFragment()
    };

    protected open var exclude = false
    protected open var optional = true
    open var fromPager = true
    abstract fun getFragment(): InputFragment
    fun getType() = type

    companion object {

        const val EXPIRATION_TYPE = "expiration"
        const val SECURITY_CODE_TYPE = "security_code"
        private val additionalSteps = mutableListOf<String>()

        fun reset() {
            additionalSteps.clear()
            getOptionalSteps().forEach {
                if (it.exclude) {
                    it.exclude = false
                }
            }
        }

        fun setAdditionalSteps(steps: List<String>) {
            additionalSteps.clear()
            additionalSteps.addAll(steps)
            getOptionalSteps().forEach {
                it.exclude = !additionalSteps.contains(it.getType())
            }
        }

        private fun getOptionalSteps() = values().filter { it.optional }.toTypedArray()

        fun getValue(position: Int) = getValues()[position]

        fun getValues() = values().filter { !it.exclude }.toTypedArray()

        fun getFromPager() = values().filter { it.fromPager && !it.exclude}.toTypedArray()
    }
}
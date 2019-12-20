package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.preferences.NameOwnerPreferences
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.CardFilledData
import kotlinx.android.synthetic.main.fragment_name_card.*

/**
 * A simple [Fragment] subclass.
 */
class CardNameFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_name_card
    private lateinit var preferences: NameOwnerPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Dependencies.instance.localPreferences!!.nameOwnerPreferences

        if (savedInstanceState == null) {
            viewModel.nameLiveData.value =
                ObjectStepFactory.createDefaultStepFrom(resources, FormType.CARD_NAME.getType())
        }
    }

    override fun bindViewModel() {
        viewModel.nameLiveData.nonNullObserve(viewLifecycleOwner) { data ->
            nameCardEditText.configure(data) {

                viewModel.updateInputData(CardFilledData.Name(it))
                isInputValid = nameCardEditText.validate()

                if (nameCardEditText.hasError()) {
                    nameCardEditText.clearError()
                }
            }
            if (nameCardEditText.getText().isEmpty()) {
                nameCardEditText.setText(preferences.getNameOwner())
            }
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        super.toNext(position, move)
        if (isInputValid) {
            val nameOwner = nameCardEditText.getText()
            viewModel.cardStepInfo.nameOwner = nameOwner
            preferences.saveNameOwner(nameOwner)
        }
    }

    override fun showError() = nameCardEditText.showError()
}
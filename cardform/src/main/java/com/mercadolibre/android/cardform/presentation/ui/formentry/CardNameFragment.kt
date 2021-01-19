package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.content.ComponentCallbacks
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.di.Dependencies
import com.mercadolibre.android.cardform.di.preferences.NameOwnerPreferences
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.factory.ObjectStepFactory
import com.mercadolibre.android.cardform.presentation.model.CardFilledData
import com.mercadolibre.android.cardform.presentation.ui.custom.InputFormEditText
import com.mercadolibre.android.cardform.tracks.model.TrackSteps
import com.mercadolibre.android.cardform.tracks.model.flow.BackTrack
import com.mercadolibre.android.cardform.tracks.model.flow.NextTrack
import com.mercadolibre.android.cardform.tracks.model.name.NameClearTrack
import com.mercadolibre.android.cardform.tracks.model.name.NameValidTrack
import com.mercadolibre.android.cardform.tracks.model.name.NameView

/**
 * A simple [Fragment] subclass.
 */
internal class CardNameFragment : InputFragment() {

    override val rootLayout = R.layout.fragment_name_card
    private lateinit var preferences: NameOwnerPreferences
    private var populate = false
    private var nameCardEditText: InputFormEditText? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Dependencies.instance.localPreferences!!.nameOwnerPreferences
        nameCardEditText = view.findViewById(R.id.nameCardEditText)

        if (savedInstanceState == null) {
            viewModel.nameLiveData.value =
                ObjectStepFactory.createDefaultStepFrom(resources, FormType.CARD_NAME.getType())
        }
        with(viewModel) {

            nameCardEditText?.let {
                populate = false
                if (it.getText().isEmpty()) {
                    it.post { it.setText(preferences.getNameOwner()); populate = true }
                }

                it.addOnIconClickListener {
                    tracker.trackEvent(NameClearTrack())
                }
            }
        }
    }

    override fun bindViewModel() {
        with(viewModel) {
            nameLiveData.nonNullObserve(viewLifecycleOwner) { data ->
                nameCardEditText?.apply {
                    configure(data) {
                        updateInputData(CardFilledData.Name(it))
                        isInputValid = validate()
                        clearError()
                    }
                }
            }
        }
    }

    override fun toNext(position: Int, move: MoveTo) {
        super.toNext(position, move)
        if (isInputValid) {
            nameCardEditText?.getText()?.let { nameOwner ->
                with(viewModel) {
                    tracker.trackEvent(NameValidTrack())
                    tracker.trackEvent(NextTrack(TrackSteps.NAME.getType()))
                    cardStepInfo.nameOwner = nameOwner
                }
                preferences.saveNameOwner(nameOwner)
            }
        }
    }

    override fun toBack(position: Int, move: MoveTo) {
        super.toBack(position, move)
        viewModel.tracker.trackEvent(BackTrack(TrackSteps.NAME.getType()))
    }

    override fun showError() {
        nameCardEditText?.showError()
    }

    override fun refreshData() {
        nameCardEditText?.setText("")
    }

    override fun trackFragmentView() {
        viewModel.tracker.trackView(NameView(populate))
    }

    override fun getSharedViewModelScope() = parentFragment!!
}
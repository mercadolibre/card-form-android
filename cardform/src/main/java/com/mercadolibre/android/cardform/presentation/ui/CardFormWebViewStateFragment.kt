package com.mercadolibre.android.cardform.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.base.BaseFragment
import com.mercadolibre.android.cardform.di.sharedViewModel
import com.mercadolibre.android.cardform.presentation.extensions.invisible
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.visible
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import com.mercadolibre.android.cardform.presentation.viewmodel.webview.CardFormWebViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CardFormWebViewStateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ICON_FROM_EXTRA = "icon_from_state"
private const val ICON_TO_EXTRA = "icon_to_state"

internal class CardFormWebViewStateFragment : BaseFragment<CardFormWebViewModel>() {

    override val viewModel: CardFormWebViewModel by sharedViewModel { activity!! }
    override val rootLayout = R.layout.fragment_card_form_web_view_state

    private lateinit var retryButton: AndesButton
    private lateinit var backButton: AndesButton
    private lateinit var iconToImage: ImageView
    private lateinit var fromToImage: ImageView
    private lateinit var progressState: FrameLayout
    private lateinit var titleProgressState: TextView
    private lateinit var descriptionProgressState: TextView
    private lateinit var closeButton: ImageButton
    private var iconFromRes: Int = 0
    private var iconToRes: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retryButton = view.findViewById(R.id.retry_button)
        backButton = view.findViewById<AndesButton>(R.id.back_button).also(::setOnBackPressAction)
        closeButton =
            view.findViewById<ImageButton>(R.id.progress_state_close).also(::setOnBackPressAction)
        iconToImage = view.findViewById(R.id.go_to_image)
        fromToImage = view.findViewById(R.id.from_to_image)
        progressState = view.findViewById(R.id.progress_state)
        titleProgressState = view.findViewById(R.id.title_progress_state)
        descriptionProgressState = view.findViewById(R.id.description_progress_state)
        retryButton.setOnClickListener { viewModel.retry() }

        savedInstanceState?.let {
            setNavigationIcons(it.getInt(ICON_TO_EXTRA), it.getInt(ICON_FROM_EXTRA))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(ICON_FROM_EXTRA, iconFromRes)
            putInt(ICON_TO_EXTRA, iconToRes)
        }
    }

    override fun bindViewModel() {
        viewModel.webUiStateLiveData.nonNullObserve(viewLifecycleOwner) {
            context?.let { context ->
                when (it) {
                    WebUiState.WebSuccess -> {
                        setLayoutState(it.layoutState)
                        viewModel.canGoBack(true)
                    }

                    WebUiState.WebProgressStart -> {
                        setLayoutState(it.layoutState)
                        setTitleAndDescriptionState(
                            it.getTitle(context),
                            it.getDescription(context)
                        )
                        setVisibilityErrorButtons(false)
                        viewModel.canGoBack(false)
                        setNavigationIcons(it.getIconTo(context), it.getIconFrom(context))
                    }

                    WebUiState.WebProgressBack -> {
                        setLayoutState(it.layoutState)
                        setTitleAndDescriptionState(
                            it.getTitle(context),
                            it.getDescription(context)
                        )
                        setVisibilityErrorButtons(false)
                        viewModel.canGoBack(false)
                        setNavigationIcons(it.getIconTo(context), it.getIconFrom(context))
                    }

                    WebUiState.WebError -> {
                        setLayoutState(it.layoutState)
                        setTitleAndDescriptionState(
                            it.getTitle(context),
                            it.getDescription(context)
                        )
                        setVisibilityErrorButtons(true)
                        viewModel.canGoBack(true)
                    }
                }
            }
        }
    }

    private fun setOnBackPressAction(view: View?) {
        view?.setOnClickListener { activity?.onBackPressed() }
    }

    private fun setVisibilityErrorButtons(beVisible: Boolean) {
        if (beVisible) {
            retryButton.visible()
            backButton.visible()
            closeButton.visible()
        } else {
            retryButton.invisible()
            backButton.invisible()
            closeButton.invisible()
        }
    }

    private fun setTitleAndDescriptionState(title: String, description: String) {
        titleProgressState.text = title
        descriptionProgressState.text = description
    }

    private fun setNavigationIcons(iconTo: Int, iconFrom: Int) {
        context?.let { context ->
            iconFromRes = iconFrom
            iconToRes = iconTo
            iconToImage.setImageDrawable(ContextCompat.getDrawable(context, iconToRes))
            fromToImage.setImageDrawable(ContextCompat.getDrawable(context, iconFromRes))
        }
    }

    private fun setLayoutState(layoutState: Int) {
        with(progressState) {
            removeAllViews()
            addView(inflate(context, layoutState, null))
        }
    }

    companion object {
        const val TAG = "state_web_view"

        @JvmStatic
        fun newInstance() = CardFormWebViewStateFragment()
    }
}
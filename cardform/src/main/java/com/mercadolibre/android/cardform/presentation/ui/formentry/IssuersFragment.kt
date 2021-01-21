package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.accessibility.AccessibilityEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.data.model.response.Issuer
import com.mercadolibre.android.cardform.internal.CardFormWithFragment
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.extensions.setAnimationListener
import com.mercadolibre.android.cardform.presentation.extensions.setupForAccessibility
import com.mercadolibre.android.cardform.presentation.helpers.KeyboardHelper
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController
import com.mercadolibre.android.cardform.tracks.model.issuers.IssuerCloseTrack
import com.mercadolibre.android.cardform.tracks.model.issuers.IssuerSelectedTrack
import com.mercadolibre.android.cardform.tracks.model.issuers.IssuersView
import com.mercadolibre.android.picassodiskcache.PicassoDiskLoader
import com.mercadolibre.android.ui.widgets.MeliButton
import kotlinx.android.synthetic.main.fragment_issuers.*

internal class IssuersFragment : InputFragment() {
    override val rootLayout = R.layout.fragment_issuers

    private lateinit var issuerAdapter: IssuerAdapter
    private var isIssuerSelected = false
    private var issuerSelected: Issuer? = null
    private var isIssuerViewTracked = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issuerList.layoutManager = LinearLayoutManager(context)
        issuerList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        select.setOnClickListener {
            isIssuerSelected = true
            issuerSelected?.let {
                with(viewModel) {
                    updateIssuer(it)
                    tracker.trackEvent(IssuerSelectedTrack(it.id))
                }
            }
            activity?.onBackPressed()
        }
        savedInstanceState?.let {
            isIssuerViewTracked = it.getBoolean(TRACK_ISSUER_VIEW, false)
        }
        setupForAccessibility()
        issuerList.apply {
            viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        getChildAt(0)?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                    }
                })
        }
    }

    override fun bindViewModel() {
        viewModel.issuersLiveData.nonNullObserve(viewLifecycleOwner) { list ->
            val filterList = list.filter { !it.imageUrl.isNullOrEmpty() }
            issuerAdapter = IssuerAdapter(filterList)
            issuerList.adapter = issuerAdapter
            if (!isIssuerViewTracked) {
                isIssuerViewTracked = true
                viewModel.tracker.trackView(IssuersView(filterList.size))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(TRACK_ISSUER_VIEW, isIssuerViewTracked)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return nextAnim.takeIf { it != 0 }?.let {
            AnimationUtils.loadAnimation(activity, it)
        }?.apply {
            if (enter) {
                setAnimationListener(start = {
                    KeyboardHelper.hideKeyboard(this@IssuersFragment)
                })
            } else {
                setAnimationListener(finish = {
                    FragmentNavigationController.toBack()
                    if (!isIssuerSelected) {
                        KeyboardHelper.showKeyboard(this@IssuersFragment)
                    }
                })
            }
        }
    }

    inner class IssuerAdapter(private val issuers: List<Issuer>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var lastIssuerSelected: IssuerViewHolder? = null
        private var lastPositionSelected: Int? = null
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return if (position == HEADER_TYPE) {
                HeaderIssuerViewHolder(inflater.inflate(R.layout.issuer_header, parent, false))
            } else {
                IssuerViewHolder(inflater.inflate(R.layout.issuer_item, parent, false))
            }
        }

        override fun getItemCount() = issuers.size + 1

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) {
                HEADER_TYPE
            } else {
                ISSUER_TYPE
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is IssuerViewHolder) {
                val validPosition = position - 1
                val issuer = issuers[validPosition]

                holder.isChecked(lastPositionSelected != null && lastPositionSelected == validPosition)
                holder.loadImage(issuer.imageUrl)
                holder.setContentDescription(issuer.name)
                holder.setOnClickListener { radioButton ->
                    lastIssuerSelected
                        ?.takeIf { it.radioButton.isChecked }
                        ?.radioButton
                        ?.isChecked = false

                    lastIssuerSelected = holder
                    lastPositionSelected = validPosition
                    select.state = MeliButton.State.NORMAL
                    radioButton.isChecked = true
                    issuerSelected = issuer
                }
            }
        }
    }

    inner class HeaderIssuerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    @SuppressLint("RestrictedApi")
    inner class IssuerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioButton: AppCompatRadioButton = itemView.findViewById(R.id.radioButton)
        private val issuerImage: ImageView = itemView.findViewById(R.id.issuerImage)

        fun loadImage(imageUrl: String?) {
            PicassoDiskLoader
                .get(issuerImage.context)
                .load(imageUrl)
                .into(issuerImage)
        }

        fun isChecked(isChecked: Boolean) {
            radioButton.isChecked = isChecked
        }

        fun setContentDescription(description: String) {
            itemView.contentDescription = description
        }

        fun setOnClickListener(onClick: (AppCompatRadioButton)-> Unit) {
            itemView.setOnClickListener {
                if (!radioButton.isChecked) {
                    onClick(radioButton)
                }
            }
        }
    }

    override fun getInputTag() = "issuers"

    override fun trackFragmentView() = Unit

    override fun onDetach() {
        super.onDetach()
        with(viewModel) {
            if (isIssuerSelected) {
                associateCard()
            } else {
                tracker.trackEvent(IssuerCloseTrack())
            }
        }
    }

    override fun getSharedViewModelScope() = activity
        ?.supportFragmentManager
        ?.findFragmentByTag(CardFormWithFragment.TAG)!!

    companion object {
        private const val HEADER_TYPE = -2
        private const val ISSUER_TYPE = -1
        private const val TRACK_ISSUER_VIEW = "track_issuer_view"
    }
}
package com.mercadolibre.android.cardform.presentation.ui.formentry

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mercadolibre.android.cardform.R
import com.mercadolibre.android.cardform.data.model.response.Issuer
import com.mercadolibre.android.cardform.presentation.extensions.nonNullObserve
import com.mercadolibre.android.cardform.presentation.ui.FragmentNavigationController
import com.mercadolibre.android.picassodiskcache.PicassoDiskLoader
import kotlinx.android.synthetic.main.fragment_issuers.*


class IssuersFragment : InputFragment() {
    override val rootLayout = R.layout.fragment_issuers

    private lateinit var issuerAdapter: IssuerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issuerList.layoutManager = LinearLayoutManager(context)
        issuerList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        issuerBack.setOnClickListener {
            FragmentNavigationController.toBack()
            onBack()
        }
    }

    override fun bindViewModel() {
        viewModel.issuersLiveData.nonNullObserve(viewLifecycleOwner) {
            issuerAdapter = IssuerAdapter(it)
            issuerList.adapter = issuerAdapter
        }
    }

    private fun onBack() {
        activity?.onBackPressed()
    }

    inner class IssuerAdapter(private val issuers: List<Issuer>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                val issuer = issuers[position - 1]

                with(holder.issuerImage) {
                    PicassoDiskLoader
                        .get(context)
                        .load(issuer.imageUrl)
                        .into(this)
                }

                holder.itemView.setOnClickListener {
                    with(viewModel) {
                        onBack()
                        setIssuer(issuer)
                        associateCard()
                    }
                }
            }
        }
    }

    inner class HeaderIssuerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class IssuerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val issuerImage: ImageView = itemView.findViewById(R.id.issuerImage)
    }

    companion object {
        const val TAG = "issuers"
        private const val HEADER_TYPE = -2
        private const val ISSUER_TYPE = -1
    }
}

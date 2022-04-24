package edu.mirea_ikbo0619.promofinder.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.mirea_ikbo0619.promofinder.R
import edu.mirea_ikbo0619.promofinder.databinding.PromocodeFragmentItemBinding
import edu.mirea_ikbo0619.promofinder.model.Promocode
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class PromocodesListAdapter :
    PagingDataAdapter<Promocode, PromocodesListAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Promocode>() {
            override fun areItemsTheSame(oldItem: Promocode, newItem: Promocode): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Promocode, newItem: Promocode): Boolean =
                oldItem == newItem
        }

    }

    var onItemClickListener: ((View, Promocode, Int) -> Unit)? = null
    var onItemButtonClickListener: ((View, Promocode, Int) -> Unit)? = null
    var onItemStarClickListener: ((View, Promocode, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        PromocodeFragmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    operator fun get(position: Int) = getItem(position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)
        holder.binding.description.text =
            item?.description ?: context.getString(R.string.loading_text)
        item?.let { item_ ->

            holder.binding.expiresDate.text = context.getString(
                R.string.expiration_date,
                ZonedDateTime.parse(item_.expiration).format(DateTimeFormatter.ISO_LOCAL_DATE)
            )
            holder.binding.root.setOnClickListener {
                if (position >= 0)
                    onItemClickListener?.invoke(it, item_, position)
            }
            holder.binding.getPromocode.setOnClickListener {
                if (position >= 0)
                    onItemButtonClickListener?.invoke(it, item_, position)
            }
            holder.binding.favourite.setOnClickListener {
                if (position >= 0)
                    onItemStarClickListener?.invoke(it, item_, position)
            }
        } ?: run {
            holder.binding.expiresDate.text = context.getString(R.string.loading_text)
        }
    }

    inner class ViewHolder(val binding: PromocodeFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

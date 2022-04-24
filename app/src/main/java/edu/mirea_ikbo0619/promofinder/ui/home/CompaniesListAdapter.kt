package edu.mirea_ikbo0619.promofinder.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.mirea_ikbo0619.promofinder.databinding.CompanyFragmentItemBinding
import edu.mirea_ikbo0619.promofinder.model.Company


class CompaniesListAdapter :
    PagingDataAdapter<Company, CompaniesListAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Company>() {
            override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean =
                oldItem == newItem
        }
    }

    var onItemClickListener: ((View, Company, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        CompanyFragmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    operator fun get(position: Int) = getItem(position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.binding.companyName.text = item.name
        holder.binding.root.setOnClickListener {
            if (position >= 0)
                onItemClickListener?.invoke(it, item, position)
        }
    }

    inner class ViewHolder(val binding: CompanyFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

package com.example.githubprofilesearch.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprofilesearch.R
import com.example.githubprofilesearch.databinding.ItemListSearchResultBinding
import com.example.githubprofilesearch.services.model.Item

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder>() {

    private val items: MutableList<Item> = ArrayList()

    fun populateData(data: List<Item>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAllData() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SearchResultHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_search_result, parent, false)
    )

    override fun onBindViewHolder(holder: SearchResultAdapter.SearchResultHolder, position: Int) {
        val item = items[position]
        with(holder) {
            with(binding) {
                tvName.text = item.login

                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .circleCrop()
                    .into(ivProfile)
            }
        }
    }

    override fun getItemCount() = items.size

    inner class SearchResultHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListSearchResultBinding.bind(view)
    }
}
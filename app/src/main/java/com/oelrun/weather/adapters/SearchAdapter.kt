package com.oelrun.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.adapters.viewholders.SearchViewHolder
import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.databinding.ListItemSearchBinding

class SearchAdapter( val clickListener: (pos: Int) -> Unit)
    : ListAdapter<CityTemp, SearchViewHolder>(SearchDiffCallback()) {

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemSearchBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }
}

class SearchDiffCallback: DiffUtil.ItemCallback<CityTemp>() {
    override fun areItemsTheSame(oldItem: CityTemp, newItem: CityTemp): Boolean {
        return oldItem.cityId == newItem.cityId
    }

    override fun areContentsTheSame(oldItem: CityTemp, newItem: CityTemp): Boolean {
        return oldItem == newItem
    }
}
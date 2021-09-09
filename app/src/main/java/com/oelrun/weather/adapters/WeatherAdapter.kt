package com.oelrun.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.oelrun.weather.R
import com.oelrun.weather.adapters.viewholders.SearchViewHolder
import com.oelrun.weather.adapters.viewholders.WeatherViewHolder
import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.database.entity.relation.WeatherFull
import com.oelrun.weather.databinding.ListItemWeatherCityBinding
import com.oelrun.weather.network.response.ObjectWeatherResponse

class WeatherAdapter: ListAdapter<WeatherFull, WeatherViewHolder>(WeatherDiffCallback()) {

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemWeatherCityBinding.inflate(layoutInflater, parent, false)
        return WeatherViewHolder(binding)
    }
}

class WeatherDiffCallback: DiffUtil.ItemCallback<WeatherFull>() {
    override fun areItemsTheSame(oldItem: WeatherFull, newItem: WeatherFull): Boolean {
        return oldItem.cityId == newItem.cityId
    }

    override fun areContentsTheSame(oldItem: WeatherFull, newItem: WeatherFull): Boolean {
        return oldItem == newItem
    }
}
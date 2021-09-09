package com.oelrun.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.adapters.viewholders.HourViewHolder
import com.oelrun.weather.database.entity.HourWeather
import com.oelrun.weather.databinding.ListItemForecastHourBinding
import com.oelrun.weather.network.response.HourWeatherResponse

class HourAdapter(private val timezoneOffset: Int): RecyclerView.Adapter<HourViewHolder>() {

    var list = listOf<HourWeather>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemForecastHourBinding.inflate(layoutInflater, parent, false)
        return HourViewHolder(binding, timezoneOffset)
    }
}
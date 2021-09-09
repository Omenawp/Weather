package com.oelrun.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.adapters.viewholders.DayViewHolder
import com.oelrun.weather.database.entity.DayWeather
import com.oelrun.weather.databinding.ListItemForecastDayBinding
import com.oelrun.weather.network.response.DayWeatherResponse

class DayAdapter(private val timezoneOffset: Int): RecyclerView.Adapter<DayViewHolder>() {

    var list = listOf<DayWeather>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemForecastDayBinding.inflate(layoutInflater, parent, false)
        return DayViewHolder(binding, timezoneOffset)
    }
}
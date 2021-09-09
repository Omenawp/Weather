package com.oelrun.weather.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.database.entity.HourWeather
import com.oelrun.weather.databinding.ListItemForecastHourBinding
import com.oelrun.weather.network.response.HourWeatherResponse
import com.oelrun.weather.utils.setWeather
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class HourViewHolder(private val binding: ListItemForecastHourBinding, timezoneOffset: Int)
    : RecyclerView.ViewHolder(binding.root) {

    private val offset = ZoneOffset.ofTotalSeconds(timezoneOffset)

    fun bind(item: HourWeather) {
        val date = if(adapterPosition != 0) {
            Instant.ofEpochSecond(item.dataStamp).atOffset(offset).toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"))
        } else { "now" }

        binding.time.text = date

        binding.temp.text = "${item.temp.roundToInt()}°"
        binding.windSpeed.text = "${item.windSpeed} км/ч"
        binding.imageWeather.setWeather(item.iconCode)
    }
}
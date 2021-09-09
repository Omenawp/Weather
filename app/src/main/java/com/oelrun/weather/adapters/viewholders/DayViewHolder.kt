package com.oelrun.weather.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.database.entity.DayWeather
import com.oelrun.weather.databinding.ListItemForecastDayBinding
import com.oelrun.weather.network.response.DayWeatherResponse
import com.oelrun.weather.utils.setWeather
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class DayViewHolder(private val binding: ListItemForecastDayBinding, timezoneOffset: Int)
    : RecyclerView.ViewHolder(binding.root) {

    private val offset = ZoneOffset.ofTotalSeconds(timezoneOffset)

    fun bind(item: DayWeather) {

        val date = Instant.ofEpochSecond(item.dataStamp).atOffset(offset).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd.MM"))

        binding.data.text = date
        val temp = "${item.tempMax.roundToInt()}° / ${item.tempMin.roundToInt()}°"
        binding.temp.text = temp
        binding.weatherDescription.text = item.description
        binding.weatherImage.setWeather(item.iconCode)
    }
}
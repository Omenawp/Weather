package com.oelrun.weather.adapters.viewholders

import android.graphics.Color
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.R
import com.oelrun.weather.adapters.DayAdapter
import com.oelrun.weather.adapters.HourAdapter
import com.oelrun.weather.database.entity.relation.WeatherFull
import com.oelrun.weather.databinding.ListItemWeatherCityBinding
import com.oelrun.weather.network.response.ObjectWeatherResponse
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class WeatherViewHolder(private val binding: ListItemWeatherCityBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeatherFull) {
        val current = item.current
        val offset = ZoneOffset.ofTotalSeconds(item.timezone)

        binding.tempNow.text = "${current.temperature.roundToInt()}°"
        binding.weatherNow.text = current.description
        binding.sunrise.text = Instant.ofEpochSecond(current.sunrise).atOffset(offset).toLocalTime()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
        binding.sunset.text = Instant.ofEpochSecond(current.sunset).atOffset(offset).toLocalTime()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
        binding.feel.text = "${current.feelsLike.roundToInt()}℃"
        binding.rain.text = "${current.rain}мм"
        binding.wind.text = "${current.windSpeed} км/ч"
        binding.humidity.text = "${current.humidity}%"
        binding.pressure.text = "${current.pressure}mmHg"
        binding.uvIndex.text = current.uvIndex.roundToInt().toString()

        val hourAdapter = HourAdapter(item.timezone)
        item.hoursWeather?.let{ hourAdapter.list = it }
        binding.listForecastHours.adapter = hourAdapter

        val dayAdapter = DayAdapter(item.timezone)
        item.daysWeather?.let{ dayAdapter.list = it }
        binding.listForecastDays.adapter = dayAdapter
    }
}
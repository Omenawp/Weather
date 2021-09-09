package com.oelrun.weather.network.response

import com.oelrun.weather.database.entity.DayWeather
import com.oelrun.weather.database.entity.HourWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayWeatherResponse (
    @SerialName("dt")
    val dataStamp: Long,
    @SerialName("temp")
    val temperature: Temperature,
    @SerialName("weather")
    val detailResponse: List<DetailResponse>
)

fun DayWeatherResponse.convertToEntity(cityId: Long): DayWeather {
    val detail = detailResponse[0]
    return DayWeather(cityId, dataStamp, temperature.min,
        temperature.max, detail.description, detail.icon)
}
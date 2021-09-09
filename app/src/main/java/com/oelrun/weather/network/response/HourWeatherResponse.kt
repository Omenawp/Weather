package com.oelrun.weather.network.response

import com.oelrun.weather.database.entity.HourWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourWeatherResponse (
    @SerialName("dt")
    val dataStamp: Long,
    @SerialName("temp")
    val temperature: Float,
    @SerialName("wind_speed")
    val windSpeed: Float,
    @SerialName("weather")
    val detailResponse: List<DetailResponse>
)

fun HourWeatherResponse.convertToEntity(cityId: Long): HourWeather {
    val detail = detailResponse[0]
    return HourWeather(cityId, dataStamp, temperature, windSpeed, detail.description, detail.icon)
}
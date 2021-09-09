package com.oelrun.weather.network.response

import com.oelrun.weather.database.entity.CurrentWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse (
    @SerialName("dt")
    val dataStamp: Long,
    @SerialName("sunrise")
    val sunrise: Long? = null,
    @SerialName("sunset")
    val sunset: Long? = null,
    @SerialName("temp")
    val temperature: Float,
    @SerialName("feels_like")
    val feelsLike: Float,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("uvi")
    val uvIndex: Float,
    @SerialName("wind_speed")
    val windSpeed: Float,
    @SerialName("weather")
    val detailResponse: List<DetailResponse>,
    @SerialName("rain")
    val rain: Rain? = null
)

@Serializable
data class Rain (
    @SerialName("1h")
    val hour: Float
)


fun CurrentWeatherResponse.convertToEntity(cityId: Long): CurrentWeather {
    val detail = detailResponse[0]
    val rain = rain?.hour ?: 0f
    return CurrentWeather(cityId, dataStamp, sunrise ?: dataStamp, sunset ?: dataStamp,
        temperature, feelsLike, pressure, humidity, uvIndex, windSpeed,
        detail.description, rain, detail.icon )
}


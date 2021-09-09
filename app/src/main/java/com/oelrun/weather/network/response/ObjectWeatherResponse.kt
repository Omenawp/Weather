package com.oelrun.weather.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectWeatherResponse (
    @SerialName("timezone_offset")
    val timezoneOffset: Int? = null,
    @SerialName("current")
    val currentResponse: CurrentWeatherResponse? = null,
    @SerialName("hourly")
    val hourly: List<HourWeatherResponse>? = null,
    @SerialName("daily")
    val daily: List<DayWeatherResponse>? = null,
    @SerialName("message")
    val errorMessage: String? = null,
    @SerialName("cod")
    val errorCode: Int? = null
)
package com.oelrun.weather.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentWeather (
    @PrimaryKey
    val cityId: Long,
    val dataStamp: Long,
    val sunrise: Long,
    val sunset: Long,
    val temperature: Float,
    val feelsLike: Float,
    val pressure: Int,
    val humidity: Int,
    val uvIndex: Float,
    val windSpeed: Float,
    val description: String,
    val rain: Float,
    val iconCode: String
)
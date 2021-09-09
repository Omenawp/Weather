package com.oelrun.weather.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["cityId", "dataStamp"])
data class HourWeather(
    val cityId: Long,
    val dataStamp: Long,
    val temp: Float,
    val windSpeed: Float,
    val description: String,
    val iconCode: String
)
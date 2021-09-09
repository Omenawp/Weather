package com.oelrun.weather.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["cityId", "dataStamp"])
data class DayWeather(
    val cityId: Long,
    val dataStamp: Long,
    val tempMin: Float,
    val tempMax: Float,
    val description: String,
    val iconCode: String
)
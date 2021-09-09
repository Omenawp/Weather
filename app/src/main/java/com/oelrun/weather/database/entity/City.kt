package com.oelrun.weather.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City (
    @PrimaryKey
    val cityId: Long,
    val cityName: String,
    val lat: Float,
    val lon: Float,
    val timezone: Int
)
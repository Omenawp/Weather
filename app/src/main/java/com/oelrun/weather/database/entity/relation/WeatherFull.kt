package com.oelrun.weather.database.entity.relation

import androidx.room.Relation
import com.oelrun.weather.database.entity.CurrentWeather
import com.oelrun.weather.database.entity.DayWeather
import com.oelrun.weather.database.entity.HourWeather

data class WeatherFull (
    val cityId: Long,
    val cityName: String,
    val lat: Float,
    val lon: Float,
    val timezone: Int,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val current: CurrentWeather,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val daysWeather: List<DayWeather>,
    @Relation(parentColumn = "cityId", entityColumn = "cityId")
    val hoursWeather: List<HourWeather>
)
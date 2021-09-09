package com.oelrun.weather.database.entity.relation

import androidx.room.Ignore

data class CityTemp (
    val cityId: Long,
    val cityName: String,
    val lat: Float,
    val lon: Float,
    val timezone: Int,
    val temperature: Float,
    val iconCode: String,
    @Ignore
    var save: Boolean
){
    constructor(cityId: Long, cityName: String, lat: Float, lon: Float, timezone: Int,
                temperature: Float, iconCode: String) :
        this(cityId, cityName, lat, lon, timezone, temperature, iconCode, true)
}
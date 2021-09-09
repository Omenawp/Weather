package com.oelrun.weather.repository

import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.database.entity.relation.WeatherFull

interface WeatherRepository {
    suspend fun getWeatherAll(): List<WeatherFull>
    suspend fun updateWeather(cityId: Long, lat: Float, lon: Float): WeatherFull
    suspend fun updateLocation(lat: Float, lon: Float)
    suspend fun findLocation(name: String): List<CityTemp>
    suspend fun getSaveCities(): List<CityTemp>
    suspend fun saveCity(data: CityTemp)
    suspend fun deleteCity(cityId: Long)
}
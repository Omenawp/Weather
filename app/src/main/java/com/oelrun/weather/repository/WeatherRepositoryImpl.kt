package com.oelrun.weather.repository

import android.util.Log
import com.oelrun.weather.database.WeatherDao
import com.oelrun.weather.database.entity.City
import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.database.entity.relation.WeatherFull
import com.oelrun.weather.network.WeatherApiService
import com.oelrun.weather.network.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl constructor(
    private val webservice: WeatherApiService,
    private val database: WeatherDao
): WeatherRepository {

    override suspend fun getWeatherAll(): List<WeatherFull> = withContext(Dispatchers.IO) {
        database.getFullWeatherInfo()
    }

    override suspend fun getSaveCities(): List<CityTemp> = withContext(Dispatchers.IO) {
        database.getSaveCities()
    }

    override suspend fun updateWeather(cityId: Long, lat: Float, lon: Float): WeatherFull {
        try { updateWeatherFromNetwork(cityId, lat, lon) } catch(e: Exception) {
            throw Exception(e.message)
        }

        return database.getFullWeatherInfoById(cityId)[0]
    }

    override suspend fun updateLocation(lat: Float, lon: Float) {
        val data = withContext(Dispatchers.IO) {
            try {
                webservice.findCityByLatLong(lat, lon)
            } catch(e: Exception) {
                ObjectFindCityResponse(errorMessage = e.message ?: "Unknown error")
            }
        }

        data.errorMessage?.let { throw Exception(it) }

        val city = data.convertToCityEntity(0)
        if(city != null) {
            database.insertCity(city)
            updateWeatherFromNetwork(0, city.lat, city.lon)
        }
    }

    override suspend fun findLocation(search: String): List<CityTemp> {
        val input = search.split(",")

        val data = withContext(Dispatchers.IO) {
            try {
                if(input.size > 1) {
                    webservice.findCityByLatLong(input[0].toFloat(), input[1].toFloat())
                } else {
                    webservice.findCityByName(input[0])
                }
            } catch(e: Exception) {
                ObjectFindCityResponse(errorMessage = e.message ?: "Unknown error")
            }
        }

        data.errorMessage?.let { return emptyList() }

        val find = data.convertToCityTempEntity()
        find?.save = false

        return if(find != null) listOf(find) else emptyList()
    }

    private suspend fun updateWeatherFromNetwork(cityId: Long, lat: Float, lon: Float) {
        val networkData = withContext(Dispatchers.IO) {
            try {
                webservice.getWeatherByCoordinates(lat, lon)
            } catch(e: Exception) {
                ObjectWeatherResponse(errorMessage = e.message ?: "Unknown error")
            }
        }

        networkData.errorMessage?.let { throw Exception(it) }

        val current = networkData.currentResponse?.convertToEntity(cityId)
        val daily = networkData.daily?.map { it.convertToEntity(cityId) }
        val hourly = networkData.hourly?.map { it.convertToEntity(cityId) }

        if(current != null && daily != null && hourly != null) {
            database.clearWeatherForUpdate(cityId)
            database.insertCurrent(current)
            database.insertDay(*daily.toTypedArray())
            database.insertHour(*hourly.toTypedArray())
        } else {
            throw Exception("Unknown error")
        }
    }

    override suspend fun saveCity(data: CityTemp) {
        try {
            val city = City(data.cityId, data.cityName, data.lat, data.lon, data.timezone)
            updateWeatherFromNetwork(data.cityId, data.lat, data.lon)
            database.insertCity(city)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun deleteCity(cityId: Long) {
        withContext(Dispatchers.IO) {
            database.clearCityInfo(cityId)
        }
    }
}
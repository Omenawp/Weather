package com.oelrun.weather.network

import com.oelrun.weather.network.response.ObjectFindCityResponse
import com.oelrun.weather.network.response.ObjectWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("onecall")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String = "minutely,alerts"
    ): ObjectWeatherResponse

    @GET("weather")
    suspend fun findCityByLatLong(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float
    ): ObjectFindCityResponse

    @GET("weather")
    suspend fun findCityByName(
        @Query("q") cityName: String
    ): ObjectFindCityResponse
}
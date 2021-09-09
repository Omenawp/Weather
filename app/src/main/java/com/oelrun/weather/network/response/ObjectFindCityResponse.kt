package com.oelrun.weather.network.response

import com.oelrun.weather.database.entity.City
import com.oelrun.weather.database.entity.relation.CityTemp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectFindCityResponse(
    @SerialName("coord")
    val latLong: Coord? = null,
    @SerialName("weather")
    val weather: List<DetailResponse>? = null,
    @SerialName("main")
    val temp: Temp? = null,
    @SerialName("timezone")
    val timezone: Int? = null,
    @SerialName("id")
    val cityId: Long? = null,
    @SerialName("name")
    val cityName: String? = null,
    @SerialName("message")
    val errorMessage: String? = null,
    @SerialName("cod")
    val errorCode: Int? = null
)

@Serializable
data class Temp (
    @SerialName("temp")
    val temp: Float
)

@Serializable
data class Coord(
    @SerialName("lon")
    val lon: Float,
    @SerialName("lat")
    val lat: Float
)

fun ObjectFindCityResponse.convertToCityEntity(id: Long? = null): City? {
    return if(latLong != null && temp != null && cityId != null && cityName != null
        && timezone != null) {
        City(id?: cityId, cityName, latLong.lat, latLong.lon, timezone)
    } else {
        null
    }
}

fun ObjectFindCityResponse.convertToCityTempEntity(): CityTemp? {
    return if(latLong != null && temp != null && cityId != null && cityName != null
        && timezone != null && weather != null) {
        CityTemp(cityId, cityName, latLong.lat, latLong.lon, timezone, temp.temp, weather[0].icon)
    } else {
        null
    }
}
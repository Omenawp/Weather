package com.oelrun.weather.database

import androidx.room.*
import com.oelrun.weather.database.entity.City
import com.oelrun.weather.database.entity.CurrentWeather
import com.oelrun.weather.database.entity.DayWeather
import com.oelrun.weather.database.entity.HourWeather
import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.database.entity.relation.WeatherFull

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(vararg city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrent(vararg cityCurrentWeather: CurrentWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(vararg dayWeather: DayWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHour(vararg hourWeather: HourWeather)

    @Transaction
    @Query("SELECT * FROM city")
    suspend fun getFullWeatherInfo(): List<WeatherFull>

    @Transaction
    @Query("SELECT * FROM city WHERE cityId=:cityId")
    suspend fun getFullWeatherInfoById(cityId: Long): List<WeatherFull>

    @Transaction
    @Query("SELECT city.cityId, city.cityName, city.lat, city.lon, city.timezone, currentweather.temperature, currentweather.iconCode FROM city LEFT JOIN currentweather ON city.cityId=currentweather.cityId")
    suspend fun getSaveCities(): List<CityTemp>

    @Transaction
    suspend fun clearCityInfo(cityId: Long) {
        clearCity(cityId)
        clearCurrent(cityId)
        clearDays(cityId)
        clearHours(cityId)
    }

    @Query("DELETE FROM city WHERE cityId = :cityId")
    suspend fun clearCity(cityId: Long)

    @Query("DELETE FROM currentweather WHERE cityId = :cityId")
    suspend fun clearCurrent(cityId: Long)

    @Transaction
    suspend fun clearWeatherForUpdate(cityId: Long) {
        clearDays(cityId)
        clearHours(cityId)
    }

    @Query("DELETE FROM dayweather WHERE cityId = :cityId")
    suspend fun clearDays(cityId: Long)

    @Query("DELETE FROM hourweather WHERE cityId = :cityId")
    suspend fun clearHours(cityId: Long)

}


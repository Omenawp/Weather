package com.oelrun.weather.screens.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oelrun.weather.database.AppDatabase
import com.oelrun.weather.database.entity.relation.WeatherFull
import com.oelrun.weather.network.WeatherApiClient
import com.oelrun.weather.network.response.Coord
import com.oelrun.weather.network.response.ObjectWeatherResponse
import com.oelrun.weather.repository.WeatherRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    private val _weatherData = MutableLiveData<List<WeatherFull>?>()
    val weatherData: LiveData<List<WeatherFull>?> = _weatherData

    private var _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private var _updated = MutableLiveData(false)
    val updated: LiveData<Boolean> = _updated

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val repository = WeatherRepositoryImpl(
        WeatherApiClient.service,
        AppDatabase.getInstance(application.applicationContext).weatherDao()
    )

    fun updateLocation(lat: Float, lon: Float) {
        viewModelScope.launch {
            _updated.value = false
            repository.updateLocation(lat, lon)
            _updated.value = true
            getWeatherData()
        }
    }

    fun updateWeather(cityId: Long, lat: Float, lon: Float, pos: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val new = repository.updateWeather(cityId, lat, lon)
                val list = _weatherData.value?.toMutableList()
                if(list.isNullOrEmpty()) list?.add(new) else {
                    list[pos] = new
                }
                _weatherData.value = list
            } catch (e:Exception) {
                _errorMessage.value = e.message
            }
            _loading.value = false
        }
    }

    fun getWeatherData() {
        _loading.value = true
        viewModelScope.launch {
            try {
                _weatherData.value = null
                _weatherData.value = withContext(Dispatchers.IO) {
                    repository.getWeatherAll()
                }
            } catch (e:Exception) {
                _errorMessage.value = e.message
            }
            _loading.value = false
        }
    }

    fun clearAll() {
        _weatherData.value = emptyList()
    }

    fun deleteLocation() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteCity(0)
            }
            getWeatherData()
        }
    }
}
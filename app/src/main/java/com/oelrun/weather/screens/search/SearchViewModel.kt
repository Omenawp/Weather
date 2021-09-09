package com.oelrun.weather.screens.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oelrun.weather.database.AppDatabase
import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.network.WeatherApiClient
import com.oelrun.weather.repository.WeatherRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val _cityData = MutableLiveData<List<CityTemp>?>()
    val cityData: LiveData<List<CityTemp>?> = _cityData

    private var _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val repository = WeatherRepositoryImpl(
        WeatherApiClient.service,
        AppDatabase.getInstance(application.applicationContext).weatherDao()
    )

    private var _closeSearch = MutableLiveData<Boolean>()
    val closeSearch: LiveData<Boolean> = _closeSearch

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private var _search = false
    val search
        get() = _search

    init {
        getSaveCitiesData()
    }


    private fun getSaveCitiesData() {
        viewModelScope.launch {
            try {
                _cityData.value = withContext(Dispatchers.IO) {
                    repository.getSaveCities()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun citySearch(search: String) {
        _loading.value = true
        viewModelScope.launch {
            _cityData.value = withContext(Dispatchers.IO) {
                repository.findLocation(search)
            }
            _loading.value = false
        }
    }

    fun startSearch() {
        _search = true
    }

    fun stopSearch() {
        _search = false
        getSaveCitiesData()
    }

    fun onItemClicked(pos: Int) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _cityData.value?.get(pos)?.let {
                if(it.save) {
                    deleteCity(it.cityId, pos)
                } else {
                    saveCity(it)
                }
            }
        }
    }

    private fun saveCity(item: CityTemp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveCity(item)
            withContext(Dispatchers.Main) {
                _closeSearch.value = true
                _loading.value = false
            }
        }
    }

    private fun deleteCity(cityId: Long, pos: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCity(cityId)
            val old = _cityData.value?.toMutableList()
            old?.removeAt(pos)
            withContext(Dispatchers.Main) {
                _cityData.value = old
                _loading.value = false
            }
        }
    }

    fun searchIsClosed() {
        _closeSearch.value = false
    }
}
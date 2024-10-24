package com.hemanth.weatherapplication.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemanth.weatherapplication.data.local.dao.WeatherDao
import com.hemanth.weatherapplication.data.local.entity.WeatherEntity
import com.hemanth.weatherapplication.data.model.MainModel
import com.hemanth.weatherapplication.data.model.WeatherDataModel
import com.hemanth.weatherapplication.data.model.WeatherModel
import com.hemanth.weatherapplication.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository,
    private val weatherDao: WeatherDao
): ViewModel(){
    private val _weatherData = MutableLiveData<WeatherDataModel>()
    val weatherData: LiveData<WeatherDataModel> = _weatherData

    fun fetchDetails(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                // Fetch from API
                val details = repository.getWeatherData(city, apiKey)
                _weatherData.value = details as WeatherDataModel
                saveWeatherData(city, details)  // Save the fetched data to the database
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}")
            }
        }
    }

    fun loadCachedWeatherData(city: String) {
        viewModelScope.launch {
            try {
                // Assuming the last searched city is stored in the database or a specific city is cached
                val cachedWeather = weatherDao.getWeatherData(city)  // Custom DAO method to fetch last cached data
                cachedWeather?.let {
                    _weatherData.value = WeatherDataModel(
                        name = it.city,
                        main = MainModel(
                            temp = it.temperature,
                            humidity = it.humidity,
                            feelsLike = it.feelsLike
                        ),
                        weather = listOf(WeatherModel(it.description, it.icon))
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading cached data: ${e.message}")
            }
        }
    }

    private fun saveWeatherData(city: String, weatherData: WeatherDataModel) {
        viewModelScope.launch {
            weatherDao.insertWeatherData(
                WeatherEntity(
                    city = city,
                    temperature = weatherData.main?.temp ?: 0.0,
                    humidity = weatherData.main?.humidity ?: 0.0,
                    feelsLike = weatherData.main?.feelsLike ?: 0.0,
                    description = weatherData.weather?.get(0)?.description ?: "",
                    icon = weatherData.weather?.get(0)?.icon ?: ""
                )
            )
        }
    }

    fun loadLastWeatherData() {
        viewModelScope.launch {
            try {
                val cachedWeather = weatherDao.getLastInsertedWeatherData()
                cachedWeather?.let {
                    _weatherData.value = WeatherDataModel(
                        name = it.city,
                        main = MainModel(
                            temp = it.temperature,
                            humidity = it.humidity,
                            feelsLike = it.feelsLike
                        ),
                        weather = listOf(WeatherModel(it.description, it.icon))
                    )
                }
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error loading last inserted data: ${e.message}")
            }
        }
    }
}
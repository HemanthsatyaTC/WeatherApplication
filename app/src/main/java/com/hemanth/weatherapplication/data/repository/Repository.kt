package com.hemanth.weatherapplication.data.repository

import com.hemanth.weatherapplication.data.model.WeatherDataModel

interface Repository {
    suspend fun getWeatherData(city: String, apiKey: String): WeatherDataModel
}
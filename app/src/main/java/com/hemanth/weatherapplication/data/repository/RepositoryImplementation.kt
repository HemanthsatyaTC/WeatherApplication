package com.hemanth.weatherapplication.data.repository

import com.hemanth.weatherapplication.R
import com.hemanth.weatherapplication.data.local.dao.WeatherDao
import com.hemanth.weatherapplication.data.model.WeatherDataModel
import com.hemanth.weatherapplication.data.remote.WeatherApiInterface
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    val weatherApiInterface: WeatherApiInterface,
    val weatherDao: WeatherDao
): Repository {
    override suspend fun getWeatherData(city: String, apiKey: String): WeatherDataModel {
        return weatherApiInterface.getWeatherData(city, apiKey)
    }
}
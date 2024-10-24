package com.hemanth.weatherapplication.data.remote

import com.hemanth.weatherapplication.data.model.WeatherDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET(WeatherApiDetails.END_POINTS)
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query("APPID") apiKey: String
    ): WeatherDataModel
}
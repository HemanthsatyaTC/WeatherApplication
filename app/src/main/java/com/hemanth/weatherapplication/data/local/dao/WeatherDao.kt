package com.hemanth.weatherapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hemanth.weatherapplication.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather_data WHERE city = :city")
    suspend fun getWeatherData(city: String): WeatherEntity?


    @Query("SELECT * FROM weather_data WHERE id = :id")
    suspend fun getDataById(id: Int): WeatherEntity?

    @Query("SELECT * FROM weather_data ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedWeatherData(): WeatherEntity?

}
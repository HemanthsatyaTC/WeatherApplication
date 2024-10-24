package com.hemanth.weatherapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val temperature: Double,
    val humidity: Double,
    val feelsLike: Double,
    val description: String,
    val icon: String
)
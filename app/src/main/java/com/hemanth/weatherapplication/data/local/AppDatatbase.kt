package com.hemanth.weatherapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hemanth.weatherapplication.data.local.dao.WeatherDao
import com.hemanth.weatherapplication.data.local.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
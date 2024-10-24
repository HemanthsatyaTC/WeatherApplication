package com.hemanth.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.hemanth.weatherapplication.data.local.WeatherDatabase
import com.hemanth.weatherapplication.data.local.dao.WeatherDao
import com.hemanth.weatherapplication.data.remote.WeatherApiDetails.BASE_URL
import com.hemanth.weatherapplication.data.remote.WeatherApiInterface
import com.hemanth.weatherapplication.data.repository.Repository
import com.hemanth.weatherapplication.data.repository.RepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.weatherDao()
    }

    @Provides
    fun getGson(): Gson { //return type is how the hilt will know what to return
        //Function Name itself serves No purpose
        return Gson()
    }

    @Provides
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun getRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun getWeatherApiInterface(retrofit: Retrofit): WeatherApiInterface {
        return retrofit.create(WeatherApiInterface::class.java)
    }

    @Provides
    fun getRepository(
        weatherApiInterface: WeatherApiInterface,
        weatherDao: WeatherDao
    ): Repository {
        return RepositoryImplementation(weatherApiInterface, weatherDao)
    }


}
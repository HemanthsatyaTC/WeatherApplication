package com.hemanth.weatherapplication.data.repository

import com.hemanth.weatherapplication.data.local.dao.WeatherDao
import com.hemanth.weatherapplication.data.local.entity.WeatherEntity
import com.hemanth.weatherapplication.data.model.MainModel
import com.hemanth.weatherapplication.data.model.WeatherDataModel
import com.hemanth.weatherapplication.data.model.WeatherModel
import com.hemanth.weatherapplication.data.remote.WeatherApiInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RepositoryImplementationTest{
    private lateinit var repository: Repository
    private val weatherApiInterface: WeatherApiInterface = mock()
    private val weatherDao: WeatherDao = mock()  // Not needed for this test, but exists in Repository

    // Sample data from the API
    private val sampleApiWeatherData = WeatherDataModel(
        name = "Chicago",
        main = MainModel(temp = 295.0, humidity = 60.0, feelsLike = 293.0),
        weather = listOf(WeatherModel(description = "Clear Sky", icon = "01d"))
    )

    @Before
    fun setUp() {
        repository = RepositoryImplementation(weatherApiInterface, weatherDao)
    }

    @Test
    fun getDataFromApi() = runBlocking {
        // Arrange: Mock the API interface to return sample weather data
        whenever(weatherApiInterface.getWeatherData("Chicago", "test_api_key")).thenReturn(sampleApiWeatherData)

        // Act: Call the getWeatherData method in the repository
        val result = repository.getWeatherData("Chicago", "test_api_key")

        // Assert: Verify that the result is the expected WeatherDataModel
        assertEquals(sampleApiWeatherData, result)
    }

    @Test
    fun throwException(): Unit = runBlocking{
        // Arrange: Mock the API interface to throw an exception (simulate API error)
        whenever(weatherApiInterface.getWeatherData("Chicago", "test_api_key"))
            .thenThrow(RuntimeException("API call failed"))

        // Act & Assert: Verify that the repository throws the RuntimeException
        assertThrows(RuntimeException::class.java) {
            runBlocking {
                repository.getWeatherData("Chicago", "test_api_key")
            }
        }
    }

}
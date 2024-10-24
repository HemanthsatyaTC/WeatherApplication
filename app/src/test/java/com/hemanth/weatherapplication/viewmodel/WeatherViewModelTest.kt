package com.hemanth.weatherapplication.viewmodel

import com.hemanth.weatherapplication.data.local.dao.WeatherDao
import com.hemanth.weatherapplication.data.model.WeatherDataModel
import com.hemanth.weatherapplication.data.repository.Repository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hemanth.weatherapplication.data.local.entity.WeatherEntity
import com.hemanth.weatherapplication.data.model.MainModel
import com.hemanth.weatherapplication.data.model.WeatherModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class WeatherViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()  // Allows LiveData to work in unit tests

    private lateinit var viewModel: WeatherViewModel
    private val repository: Repository = mock()
    private val weatherDao: WeatherDao = mock()

    // Test dispatcher to replace Dispatchers.Main
    private val testDispatcher = StandardTestDispatcher()

    // Sample weather data for the test
    private val sampleWeatherData = WeatherDataModel(
        name = "Chicago",
        main = MainModel(temp = 295.0, humidity = 60.0, feelsLike = 293.0),
        weather = listOf(WeatherModel(description = "Clear Sky", icon = "01d"))
    )

    private val cachedWeatherEntity = WeatherEntity(
        city = "Chicago",
        temperature = 295.0,
        humidity = 60.0,
        feelsLike = 293.0,
        description = "Clear Sky",
        icon = "01d"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository, weatherDao)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after the tests
        Dispatchers.resetMain()
    }

    @Test
    fun fetchDetails() = runBlockingTest {
        // Arrange: Mock the repository to return the sample weather data
        whenever(repository.getWeatherData("Chicago", "test_api_key")).thenReturn(sampleWeatherData)

        // Act: Call the fetchDetails method on the ViewModel
        viewModel.fetchDetails("Chicago", "test_api_key")

        // Advance the dispatcher to trigger any suspended coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Check that the weatherData LiveData is updated correctly
        val captor = argumentCaptor<WeatherDataModel>()
        verify(repository).getWeatherData("Chicago", "test_api_key")

        // Verify that the data in LiveData matches the sample data
        assertEquals(sampleWeatherData, viewModel.weatherData.value)
    }

    @Test
    fun throwException() = runBlockingTest{
        // Arrange: Mock the repository to throw an exception (simulate API error)
        whenever(repository.getWeatherData("Chicago", "test_api_key")).thenThrow(RuntimeException("API call failed"))

        // Act: Call the fetchDetails method on the ViewModel
        viewModel.fetchDetails("Chicago", "test_api_key")

        // Advance the dispatcher to trigger any suspended coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Verify that the repository method was called and an error was logged
        verify(repository).getWeatherData("Chicago", "test_api_key")

        // Assert: Check that LiveData was not updated due to the error
        val captor = argumentCaptor<WeatherDataModel>()
        verify(repository).getWeatherData(anyOrNull(), anyOrNull())

        // Ensure that weatherData LiveData is not updated with erroneous data
        assert(viewModel.weatherData.value == null)  // Should remain null since the API call failed

    }

    @Test
    fun saveToRoom() = runBlockingTest {
        // Arrange: Mock the repository to return the sample weather data
        whenever(repository.getWeatherData("Chicago", "test_api_key")).thenReturn(sampleWeatherData)

        // Act: Call the fetchDetails method
        viewModel.fetchDetails("Chicago", "test_api_key")

        // Advance the dispatcher to run all coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Verify that the weather data is inserted into the Room database
        verify(weatherDao).insertWeatherData(
            WeatherEntity(
                city = "Chicago",
                temperature = 295.0,
                humidity = 60.0,
                feelsLike = 293.0,
                description = "Clear Sky",
                icon = "01d"
            )
        )
    }

    @Test
    fun loadDataFromRoom () = runBlockingTest {
        // Arrange: Mock the DAO to return cached weather data for "Chicago"
        whenever(weatherDao.getWeatherData("Chicago")).thenReturn(cachedWeatherEntity)

        // Act: Call the loadCachedWeatherData method
        viewModel.loadCachedWeatherData("Chicago")

        // Advance the dispatcher to run all coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Verify that the weather data was fetched from the Room database
        verify(weatherDao, times(1)).getWeatherData("Chicago")

        // Assert: Check that the weatherData LiveData is updated with the cached data
        val cachedWeatherData = WeatherDataModel(
            name = "Chicago",
            main = MainModel(temp = 295.0, humidity = 60.0, feelsLike = 293.0),
            weather = listOf(WeatherModel(description = "Clear Sky", icon = "01d"))
        )

        // Ensure the LiveData has the cached weather data
        assert(viewModel.weatherData.value == cachedWeatherData)
    }

    @Test
    fun noDataInRoom() = runBlockingTest {
        whenever(weatherDao.getWeatherData("Chicago")).thenReturn(null)

        // Act: Call the loadCachedWeatherData method
        viewModel.loadCachedWeatherData("Chicago")

        // Advance the dispatcher to run all coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Verify that the weather data was fetched from the Room database
        verify(weatherDao, times(1)).getWeatherData("Chicago")

        // Assert: Check that the weatherData LiveData is null (or remains unchanged)
        assert(viewModel.weatherData.value == null)
    }




}


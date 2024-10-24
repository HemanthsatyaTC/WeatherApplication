package com.hemanth.weatherapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hemanth.weatherapplication.R
import com.hemanth.weatherapplication.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Preview
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("") }
    val weatherData by viewModel.weatherData.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadLastWeatherData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5D90E3)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        )
        {
            Text(
                text = "Weather App",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter city name or city code or State code") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (city.isNotEmpty()) {
                            val apiKey = context.getString(R.string.api_key)
                            viewModel.fetchDetails(city, apiKey)
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (city.isNotEmpty()) {
                        val apiKey = context.getString(R.string.api_key)
                        viewModel.fetchDetails(city, apiKey)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Weather")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = " ${weatherData?.name}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))

            weatherData?.let { data ->

                CircularWeatherDisplay(
                    temperature = kelvinToCelsius(data.main?.temp ?: 0.0),
                    humidity = data.main?.humidity.toString(),
                    feels = kelvinToCelsius(data.main?.feelsLike ?: 0.0),
                    windSpeed = data.wind?.speed.toString(),
                    description = data.weather?.get(0)?.description.toString()
                )

                Spacer(modifier = Modifier.height(160.dp))

                SunriseSunsetSection(
                    sunriseTime = formatUnixTime(data.sys?.sunrise?.toLong() ?: 0L),
                    sunsetTime = formatUnixTime(data.sys?.sunset?.toLong() ?: 0L)
                )
//
//            Text("Temperature: ${data.main?.temp?.let { kelvinToCelsius(it) }}°C")
//            Text("Humidity: ${data.main?.humidity}%")
//            Text("Feels Like: ${data.main?.feelsLike?.let { kelvinToCelsius(it) }}°C")
//            Text("Wind Speed: ${data.wind?.speed} m/s")
//            Text("Description: ${data.weather?.get(0)?.description}")


                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

fun kelvinToCelsius(kelvin: Double): String {
    return (kelvin - 273.15).toInt().toString()  // Converting to Celsius and formatting to display an integer
}

fun formatUnixTime(unixTime: Long): String {
    val date = Date(unixTime * 1000L)  // Convert seconds to milliseconds
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())  // Format as hour:minute AM/PM
    sdf.timeZone = TimeZone.getDefault()  // Set timezone
    return sdf.format(date)
}


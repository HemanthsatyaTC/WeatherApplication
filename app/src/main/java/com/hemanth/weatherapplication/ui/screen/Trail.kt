package com.hemanth.weatherapplication.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hemanth.weatherapplication.data.model.WeatherDataModel

@Preview
@Composable
fun Trail(){

    var city by remember { mutableStateOf("") }
   // val weatherData by viewModel.weatherData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF5D90E3)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city name or city code or State code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
//                    if (city.isNotEmpty()) {
//                        val apiKey = "1c245c29962583b9d2c4422640fa1af5"
//                        viewModel.fetchDetails(city, apiKey)
//                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
//                if (city.isNotEmpty()) {
//                    val apiKey = "1c245c29962583b9d2c4422640fa1af5"
//                    viewModel.fetchDetails(city, apiKey)
//                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            CircularWeatherDisplay(
//                temperature = "kelvinToCelsius(data.main?.temp ?: 0.0)",
//                maxTemp = "kelvinToCelsius(data.main?.tempMax ?: 0.0)",
//                minTemp = "kelvinToCelsius(data.main?.tempMin ?: 0.0)",
//                sunriseTime = "07:15",
//                sunsetTime = "17:50"
//            )


            Spacer(modifier = Modifier.height(24.dp))

            SunriseSunsetSection(
                sunriseTime = "6:45 AM",
                sunsetTime = "18:40 PM"
            )
        }
    }
}


@Composable
fun WeatherDetails(description: String, windSpeed: String, feelsLike: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Wind Speed: $windSpeed m/s")
        Text("Feels Like: $feelsLike°C")
        Text(description, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SunriseSunsetSection(sunriseTime: String, sunsetTime: String) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sunrise & Sunset", fontWeight = FontWeight.Bold)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Sunrise")
                    Text(sunriseTime)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Sunset")
                    Text(sunsetTime)
                }
            }
        }
    }
}
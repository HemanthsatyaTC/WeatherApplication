package com.hemanth.weatherapplication.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularWeatherDisplay(
    temperature: String,
    humidity: String,
    feels: String,
    windSpeed: String,
    description: String
) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()) {
        Canvas(modifier = Modifier.size(250.dp)) {
            drawCircle(
                color = Color.White,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        // Temperature in the center
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "$temperature °C", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold)
            Text(text = "humidity: $humidity ", fontSize = 14.sp)
            Text(text = "feels like: $feels °C", fontSize = 14.sp)
            Text(text = "wind speed: $windSpeed", fontSize = 14.sp)
            Text(text = "description: $description", fontSize = 14.sp)
        }
    }
}
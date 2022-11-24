package com.mateusz.workoutcustomer.menu
import com.mateusz.workoutcustomer.menu.WeatherViewModel
import androidx.lifecycle.observe
data class WeatherModel(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val imageUrl: String

)

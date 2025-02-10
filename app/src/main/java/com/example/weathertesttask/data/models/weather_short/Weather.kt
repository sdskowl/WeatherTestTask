package com.example.weathertesttask.data.models.weather_short


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("main")
    val main: String? = null
)
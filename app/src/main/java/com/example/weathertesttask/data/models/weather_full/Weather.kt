package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("main")
    val main: String? = null
)
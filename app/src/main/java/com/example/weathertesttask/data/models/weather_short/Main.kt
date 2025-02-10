package com.example.weathertesttask.data.models.weather_short


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: Double? = null
)
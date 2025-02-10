package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("temp")
    val temp: Double? = null
)
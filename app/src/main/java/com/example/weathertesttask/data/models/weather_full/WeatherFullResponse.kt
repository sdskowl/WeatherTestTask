package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class WeatherFullResponse(
    @SerializedName("city")
    val city: City? = null,
    @SerializedName("list")
    val list: List<DayInfo?>? = null
)
package com.example.weathertesttask.data.models.weather_short


import com.google.gson.annotations.SerializedName

data class WeatherShortResponse(
    @SerializedName("city")
    val city: City? = null,
    @SerializedName("list")
    val list: List<DayInfo?>? = null
)
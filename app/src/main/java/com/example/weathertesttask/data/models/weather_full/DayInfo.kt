package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class DayInfo(
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("dt_txt")
    val dtTxt: String? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("rain")
    val rain: Rain? = null,
    @SerializedName("weather")
    val weather: List<Weather?>? = null
)
package com.example.weathertesttask.data.models.weather_short


import com.google.gson.annotations.SerializedName

data class DayInfo(
    @SerializedName("dt_txt")
    val dtTxt: String? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weather: List<Weather?>? = null
)
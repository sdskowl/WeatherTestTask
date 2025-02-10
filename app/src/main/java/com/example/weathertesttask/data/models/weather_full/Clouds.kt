package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int? = null
)
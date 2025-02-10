package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val rain: Double? = null
)
package com.example.weathertesttask.data.models.weather_full


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("name")
    val name: String? = null
)
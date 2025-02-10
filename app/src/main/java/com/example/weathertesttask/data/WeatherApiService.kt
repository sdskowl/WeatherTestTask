package com.example.weathertesttask.data

import com.example.weathertesttask.data.models.weather_full.WeatherFullResponse
import com.example.weathertesttask.data.models.weather_short.WeatherShortResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    companion object {
        const val BASE_URL = "https://pro.openweathermap.org/data/2.5/forecast/"
        const val API_KEY = "e97c67d4c28ed3556315e4f0026676cb"
    }

    @GET(".")
    suspend fun getWeatherFull(
        @Query("q") city: String,
        @Query("cnt") days: Int,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherFullResponse>

    @GET(".")
    suspend fun getWeatherShort(
        @Query("q") city: String,
        @Query("cnt") days: Int,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherShortResponse>

}
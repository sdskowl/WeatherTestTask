package com.example.weathertesttask.data

import com.example.weathertesttask.base.ApiResult
import com.example.weathertesttask.base.HandlerApi
import com.example.weathertesttask.data.models.weather_full.WeatherFullResponse
import com.example.weathertesttask.data.models.weather_short.WeatherShortResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepo(
    private val weatherApiService: WeatherApiService,
    private val handlerApi: HandlerApi
) {

    suspend fun getWeatherFull(city: String, days: String): ApiResult<WeatherFullResponse, Any> {
        return withContext(Dispatchers.IO) {
            handlerApi.handle {
                weatherApiService.getWeatherFull(
                    city = city,
                    days = days.toIntOrNull() ?: 7 //default 7 days
                )
            }
        }
    }

    suspend fun getWeatherShort(city: String, days: String): ApiResult<WeatherShortResponse, Any> {
        return withContext(Dispatchers.IO) {
            handlerApi.handle {
                weatherApiService.getWeatherShort(
                    city = city,
                    days = days.toIntOrNull() ?: 7 //default 7 days
                )
            }
        }
    }

}
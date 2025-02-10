package com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.weathertesttask.base.BaseViewModel
import com.example.weathertesttask.base.onError
import com.example.weathertesttask.base.onException
import com.example.weathertesttask.base.onSuccess
import com.example.weathertesttask.data.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class SecondScreenViewModel(
    private val city: String,
    private val days: String,
    private val isFullMode: Boolean,
    private val weatherRepo: WeatherRepo
) :
    BaseViewModel<SecondScreenState, SecondScreenEvent, SecondScreenEffect>(SecondScreenState()) {
    private val TAG = this::class.simpleName

    init {
        subscribeToEvents()
        sendEvent(Fetch)
    }

    override suspend fun onEvent(event: SecondScreenEvent) {
        when (event) {
            Fetch -> viewModelScope.launch(Dispatchers.IO) {
                if (isFullMode) {
                    fetchFull()
                } else {
                    fetchShort()
                }
            }
        }
    }

    private suspend fun fetchShort() {
        weatherRepo.getWeatherShort(
            city = city,
            days = days
        ).onSuccess { response ->

            val string = buildString {
                append("Short Report:\n")
                response.list?.forEach { day ->
                    append("-------------------\n")
                    append("Date: ${day?.dtTxt}\n")
                    append("Temperature: ${day?.main?.temp}\n")
                    append("Weather Type: ${day?.weather?.get(0)?.main}\n")
                }
                append("-------------------\n")
                append("City: ${response.city?.name}")
            }
            update { state -> state.copy(result = string) }
        }.onError { response ->
            update { state -> state.copy(result = response.data.toString()) }
            if (response.data is ResponseBody) {
                update { state -> state.copy(result = response.data.string()) }
            }

        }.onException { ex ->
            ex.printStackTrace()
            update { state -> state.copy(result = ex.localizedMessage.orEmpty()) }
        }
    }

    private suspend fun fetchFull() {
        weatherRepo.getWeatherFull(
            city = city,
            days = days
        ).onSuccess { response ->
            val string = buildString {
                append("Full Report:\n")
                response.list?.forEach { day ->
                    append("-------------------\n")
                    append("Date: ${day?.dtTxt}\n")
                    append("Temperature: ${day?.main?.temp}\n")
                    append("Weather Type: ${day?.weather?.get(0)?.main}\n")
                    append("Description: ${day?.weather?.get(0)?.description}")
                    append("Pressure: ${day?.main?.pressure}\n")
                    append("Humidity: ${day?.main?.humidity}\n")
                    append("Rain: ${day?.rain?.rain}\n")
                    append("Cloud Coverage: ${day?.clouds?.all}\n")
                }
                append("-------------------\n")
                append("City: ${response.city?.name}")
            }
            update { state -> state.copy(result = string) }
        }.onError { response ->
            update { state -> state.copy(result = response.data.toString()) }
            if (response.data is ResponseBody) {
                update { state -> state.copy(result = response.data.string()) }
            }

        }.onException { ex ->
            ex.printStackTrace()
            update { state -> state.copy(result = ex.localizedMessage.orEmpty()) }
        }
    }
}
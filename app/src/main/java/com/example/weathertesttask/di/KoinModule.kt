package com.example.weathertesttask.di

import com.example.weathertesttask.base.HandlerApi
import com.example.weathertesttask.data.WeatherApiService
import com.example.weathertesttask.data.WeatherApiService.Companion.BASE_URL
import com.example.weathertesttask.data.WeatherRepo
import com.example.weathertesttask.ui.theme.screens.first_sreen.viewmodel.FirstScreenViewModel
import com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel.SecondScreenViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinModule = module {
    singleOf(::HandlerApi)
    single {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(WeatherApiService::class.java)
    }
    singleOf(::WeatherRepo)

    viewModelOf(::FirstScreenViewModel)
    viewModelOf(::SecondScreenViewModel)
}
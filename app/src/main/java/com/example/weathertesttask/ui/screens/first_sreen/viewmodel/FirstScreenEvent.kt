package com.example.weathertesttask.ui.screens.first_sreen.viewmodel

import com.example.weathertesttask.base.UiEvent


sealed interface FirstScreenEvent : UiEvent
data class OnSend(val city: String, val days: String, val isFullMode: Boolean) : FirstScreenEvent
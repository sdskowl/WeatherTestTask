package com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel

import com.example.weathertesttask.base.UiEvent


sealed interface SecondScreenEvent : UiEvent
data object Fetch : SecondScreenEvent

package com.example.weathertesttask.ui.theme.screens.first_sreen.viewmodel

import com.example.weathertesttask.base.UIEffect


sealed interface FirstScreenEffect : UIEffect
data class NavigateToSecondScreen(val city: String, val days: String, val isFullMode: Boolean) : FirstScreenEffect
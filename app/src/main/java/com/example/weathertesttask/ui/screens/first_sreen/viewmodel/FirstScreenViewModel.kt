package com.example.weathertesttask.ui.screens.first_sreen.viewmodel

import com.example.weathertesttask.base.BaseViewModel


class FirstScreenViewModel :
    BaseViewModel<FirstScreenState, FirstScreenEvent, FirstScreenEffect>(FirstScreenState()) {
    private val TAG = this::class.simpleName

    init {
        subscribeToEvents()
    }

    override suspend fun onEvent(event: FirstScreenEvent) {
        when (event) {
            is OnSend -> {
                sendEffect(
                    NavigateToSecondScreen(
                    city = event.city,
                    days = event.days,
                    isFullMode = event.isFullMode
                )
                )
            }
        }
    }
}
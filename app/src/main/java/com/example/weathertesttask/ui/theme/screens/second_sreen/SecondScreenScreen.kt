package com.example.weathertesttask.ui.theme.screens.second_sreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel.SecondScreenEvent
import com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel.SecondScreenViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SecondScreenScreen(vm: SecondScreenViewModel) {
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) {
        vm.effect.onEach { effect ->
            when (effect) {
                else -> {}
            }
        }.collect()
    }
    val event: (SecondScreenEvent) -> Unit = remember {
        { vm.sendEvent(it) }
    }
    SecondScreenContent(state = state, event = event)
}
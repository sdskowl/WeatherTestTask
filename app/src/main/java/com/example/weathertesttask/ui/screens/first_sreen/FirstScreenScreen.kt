package com.example.weathertesttask.ui.screens.first_sreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.weathertesttask.ui.screens.first_sreen.viewmodel.FirstScreenEvent
import com.example.weathertesttask.ui.screens.first_sreen.viewmodel.FirstScreenViewModel
import com.example.weathertesttask.ui.screens.first_sreen.viewmodel.NavigateToSecondScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun FirstScreenScreen(
    vm: FirstScreenViewModel = koinViewModel(),
    navigateToSecond: (city: String, days: String, reportMode: Boolean) -> Unit
) {
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) {
        vm.effect.onEach { effect ->
            when (effect) {
                is NavigateToSecondScreen -> navigateToSecond(
                    effect.city,
                    effect.days,
                    effect.isFullMode
                )
            }
        }.collect()
    }
    val event: (FirstScreenEvent) -> Unit = remember {
        { vm.sendEvent(it) }
    }
    FirstScreenContent(state = state, event = event)
}
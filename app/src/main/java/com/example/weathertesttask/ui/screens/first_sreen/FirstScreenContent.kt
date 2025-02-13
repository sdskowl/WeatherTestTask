package com.example.weathertesttask.ui.screens.first_sreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.weathertesttask.ui.screens.first_sreen.viewmodel.FirstScreenEvent
import com.example.weathertesttask.ui.screens.first_sreen.viewmodel.FirstScreenState
import com.example.weathertesttask.ui.screens.first_sreen.viewmodel.OnSend

@Composable
internal fun FirstScreenContent(
    state: FirstScreenState = FirstScreenState(),
    event: (FirstScreenEvent) -> Unit = {}
) {
    var city by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    var isFullMode by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextField(value = city, onValueChange = { city = it }, label = {
                Text(text = "city")
            })
            TextField(value = days, onValueChange = { days = it }, label = {
                Text(text = "days from 1 to 5")
            },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Column {
                Text(text = "report mode short/full")
                Switch(checked = isFullMode, onCheckedChange = { isFullMode = it })
            }
            Button(onClick = {
                event(
                    OnSend(
                        city = city,
                        days = days,
                        isFullMode = isFullMode
                    )
                )
            }) {
                Text(text = "Send")
            }
        }
    }

}
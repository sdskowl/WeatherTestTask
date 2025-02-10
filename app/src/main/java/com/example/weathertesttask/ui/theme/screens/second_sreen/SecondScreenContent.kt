package com.example.weathertesttask.ui.theme.screens.second_sreen

import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weathertesttask.R
import com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel.SecondScreenEvent
import com.example.weathertesttask.ui.theme.screens.second_sreen.viewmodel.SecondScreenState

@Composable
internal fun SecondScreenContent(
    state: SecondScreenState = SecondScreenState(),
    event: (SecondScreenEvent) -> Unit = {}
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.second_screen_layout, null)
        },
        update = { view ->
            if (view is ConstraintLayout) {
                val text = view.findViewById<TextView>(R.id.textView)
                text.text = state.result
            } else {
                Log.e("AndroidView", "couldn't find view")
            }
        },
    )
}
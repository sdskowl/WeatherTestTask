package com.example.weathertesttask.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UIEffect>(initialVal: State) :
    ViewModel() {
    private val TAG = this::class.simpleName ?: ""
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialVal)
    open val showStateLog = true
    val state: StateFlow<State> get() = _state
    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect>
        get() = _effect.onEach { effect: Effect ->
            Log.d(TAG,"effect: $effect")
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    val effectSubscription get() = _effect.subscriptionCount
    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()

    protected fun subscribeToEvents() {
        _events.onEach { event ->
            Log.d(TAG,"event: $event")
            onEvent(event)
        }.share().addDataFlows().share()
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    protected open fun Flow<Event>.addDataFlows(): Flow<Any> = flowOf(Unit)
    protected fun update(updater: (state: State) -> State) {
        _state.update(updater)
        if (showStateLog) Log.d(TAG,"state: ${state.value}")
    }

    fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected abstract suspend fun onEvent(event: Event)

    private fun <T> Flow<T>.share(): Flow<T> {
        return shareIn(scope = viewModelScope, started = SharingStarted.Eagerly)
    }
}

interface UIEffect

interface UiState

interface UiEvent

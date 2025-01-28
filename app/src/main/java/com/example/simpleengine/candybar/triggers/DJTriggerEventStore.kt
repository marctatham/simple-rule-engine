package com.example.simpleengine.candybar.triggers

import com.example.simpleengine.scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// singleton implementation that implements each of the interfaces
class DJTriggerEventStore {

    private val _state: MutableStateFlow<List<TriggerEvent>> = MutableStateFlow(emptyList())
    private val state = _state.asStateFlow()

    fun storeEvent(event: TriggerEvent) {
        scope.launch {
            _state.update { it.filterNot { it::class == event::class } + event }
        }
    }

    fun observeEvents(): StateFlow<List<TriggerEvent>> = state

    fun clearEvents() {
        scope.launch { _state.emit(emptyList()) }
    }
}
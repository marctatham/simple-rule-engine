package com.example.simpleengine.candybar.triggers

import com.example.simpleengine.scope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// singleton implementation that implements each of the interfaces
class DJTriggerEventStore {

    private val _state: MutableStateFlow<Set<TriggerEvent>> = MutableStateFlow(emptySet())
    private val state = _state.asStateFlow()

    private val events = mutableSetOf<TriggerEvent>()

    fun storeEvent(event: TriggerEvent) {
        events.add(event)
        scope.launch {
            _state.emit(events)
        }
    }

    fun observeEvents(): StateFlow<Set<TriggerEvent>> = state

    fun clearEvents() {
        events.clear()
        scope.launch { _state.emit(events) }
    }

}
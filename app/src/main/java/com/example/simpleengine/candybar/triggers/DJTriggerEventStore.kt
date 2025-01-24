package com.example.simpleengine.candybar.triggers

import com.example.simpleengine.scope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// singleton implementation that implements each of the interfaces
class DJTriggerEventStore {

    private val _state: MutableStateFlow<TriggerEvent?> = MutableStateFlow(null)
    private val state = _state.asStateFlow()

    fun storeEvent(triggerEvent: TriggerEvent) {
        scope.launch {
            _state.emit(triggerEvent)
        }
    }

    fun observeEvents(): Flow<TriggerEvent?> = state

    fun clearEvents() {
        scope.launch { _state.emit(null) }
    }

}
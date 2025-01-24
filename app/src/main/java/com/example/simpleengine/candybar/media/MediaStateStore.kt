package com.example.simpleengine.candybar.media

import com.example.simpleengine.scope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// singleton implementation that implements each of the interfaces
class MediaStateStore {

    private val _state: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val state = _state.asStateFlow()

    fun trackMediaState(isShown: Boolean) {
        scope.launch {
            _state.emit(isShown)
        }
    }

    fun observeEvents(): Flow<Boolean> = state

}
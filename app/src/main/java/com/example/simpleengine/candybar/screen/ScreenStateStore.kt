package com.example.simpleengine.candybar.screen

import com.example.simpleengine.scope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// singleton implementation that implements each of the interfaces
class ScreenStateStore {

    private val _state: MutableStateFlow<String> = MutableStateFlow("ScreenOne")
    private val state = _state.asStateFlow()

    fun trackModalState(screen:String) {
        scope.launch { _state.emit(screen) }
    }

    fun observeEvents(): Flow<String> = state

}
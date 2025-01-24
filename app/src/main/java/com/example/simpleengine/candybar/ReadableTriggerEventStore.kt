package com.example.simpleengine.candybar

import kotlinx.coroutines.flow.Flow

interface ReadableTriggerEventStore {

    fun observeEvents(): Flow<TriggerEvent>

}
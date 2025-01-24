package com.example.simpleengine.candybar

import com.dowjones.candybar.WritableTriggerEventStore
import kotlinx.coroutines.flow.Flow

// singleton implementation that implements each of the interfaces
class DJTriggerEventStore : ReadableTriggerEventStore, WritableTriggerEventStore,
    ClearableTriggerEventStore {

    override fun storeEvent(triggerEvent: TriggerEvent) {
        TODO("Not yet implemented")

        // does this write this information somewhere? like a database? like SharedPrefs? etc.
        // we need some way of resetting this information, but the responsibility of when to reset must live in the manager

    }

    override fun observeEvents(): Flow<TriggerEvent> {
        TODO("Not yet implemented")
    }

    override fun clearEvents() {
        TODO("Not yet implemented")
    }

}
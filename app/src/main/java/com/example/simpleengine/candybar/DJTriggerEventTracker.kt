package com.example.simpleengine.candybar

import com.dowjones.candybar.WritableTriggerEventStore

class DJTriggerEventTracker(
    private val triggerEventStore: WritableTriggerEventStore
) : TriggerEventTracker {

    override fun track(triggerEvent: TriggerEvent) {
        triggerEventStore.storeEvent(triggerEvent)
    }
}
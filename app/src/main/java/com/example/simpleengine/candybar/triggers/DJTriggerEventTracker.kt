package com.example.simpleengine.candybar.triggers

class DJTriggerEventTracker(
    private val triggerEventStore: DJTriggerEventStore
) : TriggerEventTracker {

    override fun track(triggerEvent: TriggerEvent) {
        triggerEventStore.storeEvent(triggerEvent)
    }
}
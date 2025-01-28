package com.example.simpleengine.candybar.triggers

import android.util.Log
import com.example.simpleengine.candybar.AppVisitsStore

class DJTriggerEventTracker(
    private val triggerEventStore: DJTriggerEventStore, private val appVisitsStore: AppVisitsStore
) : TriggerEventTracker {

    override fun track(triggerEvent: TriggerEvent) {

        when (triggerEvent) {
            is TriggerEvent.AppVisitEvent -> appVisitsStore.incrementVisitCount()
            else -> Log.v(
                "DJTriggerEventTracker",
                "no custom behaviour defined for event: ${triggerEvent.javaClass.simpleName}"
            )
        }

        triggerEventStore.storeEvent(triggerEvent)
    }
}